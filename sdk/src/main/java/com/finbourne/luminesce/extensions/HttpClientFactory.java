package com.finbourne.luminesce.extensions;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.lang.InterruptedException;

/**
* Builds http client to communicate to luminesce API instances.
*
*/
public class HttpClientFactory {

    /**
    *  Builds a {@link OkHttpClient} from a {@link ApiConfiguration} to make
    *  calls to the luminesce API.
    *
    * @param apiConfiguration configuration to connect to luminesce API
    * @return an client for http calls to luminesce API
    */
    public OkHttpClient build(ApiConfiguration apiConfiguration) {
        return this.build(apiConfiguration, 10, 10);
    }

    /**
     * Builds a {@link OkHttpClient} from a {@link ApiConfiguration} to make
     * calls to the luminesce API.
     *
     * @param apiConfiguration configuration to connect to luminesce API
     * @param readTimeout read timeout in seconds
     * @param writeTimeout write timeout in seconds
     * @return n client for http calls to luminesce API
     */
    public OkHttpClient build(ApiConfiguration apiConfiguration, int readTimeout, int writeTimeout){
        Builder builder = new OkHttpClient.Builder()
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
        ;
        //  use a proxy if given
        if (apiConfiguration.getProxyAddress() != null) {
            InetSocketAddress address = new InetSocketAddress(apiConfiguration.getProxyAddress(), apiConfiguration.getProxyPort());
            builder
                .proxy(new Proxy(Proxy.Type.HTTP, address))
                .proxyAuthenticator((route, response) -> {
                    String credential = Credentials.basic(apiConfiguration.getProxyUsername(), apiConfiguration.getProxyPassword());
                    return response.request().newBuilder()
                            .header("Proxy-Authorization", credential)
                            .build();
                })
            ;
        }
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                int tryCount = 0;
                try {
                    while (response.code() == 429 && tryCount < 3) {
                        Thread.sleep(1000);  // should take this delay from the header
                        tryCount++;
                        response.close();
                        response = chain.proceed(request);
                    }
                } catch(InterruptedException e) {}
                // otherwise just pass the original response on
                return response;
            }
        });
        return builder.build();
    }

}
