package com.finbourne.luminesce.extensions;

import com.finbourne.luminesce.ApiException;
import com.finbourne.luminesce.api.CurrentTableFieldCatalogApi;
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
         ApiFactory apiFactory = ApiFactoryBuilder.build(CredentialsSource.credentialsFile);
         assertThat(apiFactory, is(notNullValue()));
         assertThatFactoryBuiltApiCanMakeApiCalls(apiFactory);
     }

     private static void assertThatFactoryBuiltApiCanMakeApiCalls(ApiFactory apiFactory) throws ApiException {
         CurrentTableFieldCatalogApi currentTableFieldCatalogApi = apiFactory.build(CurrentTableFieldCatalogApi.class);
         String catalog = currentTableFieldCatalogApi.getCatalog(null, null);
         assertThat("API should return values"
                 , catalog, is(notNullValue()));
     }

 }
