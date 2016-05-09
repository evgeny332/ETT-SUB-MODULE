package com.spring.root;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.providers.netty.NettyAsyncHttpProvider;
import com.ning.http.client.providers.netty.NettyAsyncHttpProviderConfig;

/**
 * Created with IntelliJ IDEA.
 * User: ankur
 * Date: 14/10/13
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class HttpClientConfig {

    @Bean
    public AsyncHttpClient asyncHttpClient(){

        /*Create Nett Http provider configuration for asynchronous connect operation*/
        NettyAsyncHttpProviderConfig nettyAsyncHttpProviderConfig = new NettyAsyncHttpProviderConfig();
        nettyAsyncHttpProviderConfig.addProperty(NettyAsyncHttpProviderConfig.EXECUTE_ASYNC_CONNECT,true);
        nettyAsyncHttpProviderConfig.addProperty(NettyAsyncHttpProviderConfig.DISABLE_NESTED_REQUEST,true);


        /*Create client configuration object*/
        AsyncHttpClientConfig config =  new AsyncHttpClientConfig.Builder()
                .setAllowPoolingConnection(true)
                .setMaximumConnectionsPerHost(20000)
                .setMaximumConnectionsTotal(20000)
                .setIdleConnectionTimeoutInMs(60000)
                .setConnectionTimeoutInMs(5000)
                .setRequestTimeoutInMs(60000)
                .setAsyncHttpClientProviderConfig(nettyAsyncHttpProviderConfig)
                .setCompressionEnabled(true)
                .build();

        return new AsyncHttpClient(new NettyAsyncHttpProvider(config),config);
    }
}
