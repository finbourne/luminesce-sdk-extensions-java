package com.finbourne.luminesce.extensions;

import com.finbourne.luminesce.ApiClient;
import com.finbourne.luminesce.ApiException;
import com.finbourne.luminesce.api.HistoricallyExecutedQueriesApi;
import com.finbourne.luminesce.extensions.auth.FinbourneTokenException;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ApiExceptionTests {
 @Test
     public void thrown_exception_tostring_contains_requestid() throws ApiConfigurationException, FinbourneTokenException {

         ApiConfiguration apiConfiguration = new ApiConfigurationBuilder().build(CredentialsSource.credentialsFile);
         ApiClient apiClient = new ApiClientBuilder().build(apiConfiguration);

         HistoricallyExecutedQueriesApi historicallyExecutedQueriesApi = new HistoricallyExecutedQueriesApi(apiClient);

         try {
             historicallyExecutedQueriesApi.getProgressOfHistory("doesntExist");
         }
         catch (ApiException e) {

             String message = e.toString();

             assertNotNull("Null exception message", message);

             String[] parts = message.split("\\r?\\n");

             assertThat(parts.length, is(greaterThanOrEqualTo(1)));

             //  of the format 'LUSID request id = 000000000:AAAAAAA'
             String[] idParts = parts[0].split(" = ");

             assertThat("missing requestId", idParts.length, is(equalTo(2)));
         }
         catch (Exception e) {
             fail("Unexpected exception of type " + e.getClass());
         }


     }
}
