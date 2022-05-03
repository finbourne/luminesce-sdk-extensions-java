package com.finbourne.luminesce.extensions;

import com.finbourne.luminesce.ApiClient;
import com.finbourne.luminesce.extensions.auth.HttpFinbourneTokenProvider;
import com.finbourne.luminesce.extensions.auth.RefreshingTokenProvider;
import com.finbourne.luminesce.extensions.auth.FinbourneToken;
import com.finbourne.luminesce.extensions.auth.FinbourneTokenException;
import okhttp3.OkHttpClient;

/**
* Utility class to build an ApiClient from a set of configuration
*/
public class ApiClientBuilder {

    /**
    * Builds an ApiClient implementation configured against a secrets file. Typically used
    * for communicating with luminesce via the APIs
    *
    * ApiClient implementation enables use of REFRESH tokens (see https://support.finbourne.com/using-a-refresh-token)
    * and automatically handles token refreshing on expiry.
    *
    * @param apiConfiguration configuration to connect to luminesce API
    * @return
    *
    * @throws FinbourneTokenException on failing to authenticate and retrieve an initial {@link FinbourneToken}
    */
    public ApiClient build(ApiConfiguration apiConfiguration) throws FinbourneTokenException {
        // http client to use for api and auth calls
        OkHttpClient httpClient = createHttpClient(apiConfiguration);

        // token provider to keep client authenticated with automated token refreshing
        RefreshingTokenProvider refreshingTokenProvider = new RefreshingTokenProvider(new HttpFinbourneTokenProvider(apiConfiguration, httpClient));
        FinbourneToken finbourneToken = refreshingTokenProvider.get();

        // setup api client that managed submissions with latest token
        ApiClient defaultApiClient = createDefaultApiClient(apiConfiguration, httpClient, finbourneToken);
        return new RefreshingTokenApiClient(defaultApiClient, refreshingTokenProvider);
    }

    ApiClient createDefaultApiClient(ApiConfiguration apiConfiguration, OkHttpClient httpClient, FinbourneToken finbourneToken) throws FinbourneTokenException {
        ApiClient apiClient = createApiClient();

        if (apiConfiguration.getProxyAddress() != null) {
            apiClient.setHttpClient(httpClient);
        }

        if (finbourneToken.getAccessToken() == null) {
            throw new FinbourneTokenException("Cannot construct an API client with a null authorisation header. Ensure " +
                    "finbourne token generated is valid");
        } else {
            apiClient.addDefaultHeader("Authorization", "Bearer " + finbourneToken.getAccessToken());
        }

        if (apiConfiguration.getApplicationName() != null) {
            apiClient.addDefaultHeader("X-LUSID-Application", apiConfiguration.getApplicationName());
        }
        apiClient.setBasePath(apiConfiguration.getApiUrl());

        return  apiClient;
    }

    private OkHttpClient createHttpClient(ApiConfiguration apiConfiguration){
        return new HttpClientFactory().build(apiConfiguration);
    }

    // allows us to mock out api client for testing purposes
    ApiClient createApiClient(){
        return new ApiClient();
    }
}