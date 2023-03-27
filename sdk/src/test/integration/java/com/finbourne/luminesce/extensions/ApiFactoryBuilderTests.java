package com.finbourne.luminesce.extensions;

import com.finbourne.luminesce.ApiException;
import com.finbourne.luminesce.api.CurrentTableFieldCatalogApi;
import com.finbourne.luminesce.api.SqlExecutionApi;
import com.finbourne.luminesce.extensions.auth.FinbourneTokenException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

 public class ApiFactoryBuilderTests {

     @Rule
     public ExpectedException thrown = ExpectedException.none();

     @Test
     public void build_WithExistingConfigurationFile_ShouldReturnFactory() throws ApiException, ApiConfigurationException, FinbourneTokenException {
         ApiFactory apiFactory = ApiFactoryBuilder.build(CredentialsSource.credentialsFile, 120, 120);
         assertThat(apiFactory, is(notNullValue()));
         assertThatFactoryBuiltApiCanMakeApiCalls(apiFactory);
     }

     private static void assertThatFactoryBuiltApiCanMakeApiCalls(ApiFactory apiFactory) throws ApiException {
//         CurrentTableFieldCatalogApi currentTableFieldCatalogApi = apiFactory.build(CurrentTableFieldCatalogApi.class);
         SqlExecutionApi sea = apiFactory.build(SqlExecutionApi.class);
//         String catalog = currentTableFieldCatalogApi.getCatalog(null, null);
         String res = sea.putByQueryCsv("select * from Lusid.Instrument limit 10;", "csv-instruments", true, 120, ",", null);
         assertThat("API should return values", res, is(notNullValue()));
     }

 }
