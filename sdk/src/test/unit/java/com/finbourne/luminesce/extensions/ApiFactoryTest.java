package com.finbourne.luminesce.extensions;

import com.finbourne.luminesce.ApiClient;
import com.finbourne.luminesce.api.SqlExecutionApi;
import com.finbourne.luminesce.model.BackgroundMultiQueryResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ApiFactoryTest {

    private ApiFactory apiFactory;
    private ApiClient apiClient;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        apiClient = mock(ApiClient.class);
        apiFactory = new ApiFactory(apiClient);
    }

    // General Cases

     @Test
     public void build_ForFilesApi_ReturnFilesApi(){
         SqlExecutionApi sqlExecutionApi = apiFactory.build(SqlExecutionApi.class);
         assertThat(sqlExecutionApi, instanceOf(SqlExecutionApi.class));
     }

     @Test
     public void build_ForAnyApi_SetsTheApiFactoryClientAndNotTheDefault(){
         SqlExecutionApi sqlExecutionApi = apiFactory.build(SqlExecutionApi.class);
         assertThat(sqlExecutionApi.getApiClient(), equalTo(apiClient));
     }

     // Singleton Check Cases

     @Test
     public void build_ForSameApiBuiltAgainWithSameFactory_ReturnTheSameSingletonInstanceOfApi(){
         SqlExecutionApi sqlExecutionApi = apiFactory.build(SqlExecutionApi.class);
         SqlExecutionApi sqlExecutionApiSecond = apiFactory.build(SqlExecutionApi.class);
         assertThat(sqlExecutionApi, sameInstance(sqlExecutionApiSecond));
     }

     @Test
     public void build_ForSameApiBuiltWithDifferentFactories_ReturnAUniqueInstanceOfApi(){
         SqlExecutionApi sqlExecutionApi = apiFactory.build(SqlExecutionApi.class);
         SqlExecutionApi sqlExecutionApiSecond = new ApiFactory(mock(ApiClient.class)).build(SqlExecutionApi.class);
         assertThat(sqlExecutionApi, not(sameInstance(sqlExecutionApiSecond)));
     }

     // Error Cases

     @Test
     public void build_ForNonApiPackageClass_ShouldThrowException(){
         thrown.expect(UnsupportedOperationException.class);
         thrown.expectMessage("com.finbourne.luminesce.model.BackgroundMultiQueryResponse class is not a supported API class. " +
                 "Supported API classes live in the " + ApiFactory.API_PACKAGE + " package.");
         apiFactory.build(BackgroundMultiQueryResponse.class);
     }



}
