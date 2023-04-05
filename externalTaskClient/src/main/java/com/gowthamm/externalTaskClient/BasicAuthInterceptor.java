package com.gowthamm.externalTaskClient;

import java.util.Base64;

import org.camunda.bpm.client.interceptor.ClientRequestContext;
import org.camunda.bpm.client.interceptor.ClientRequestInterceptor;

public class BasicAuthInterceptor implements ClientRequestInterceptor {

    private String username;
    private String password;

    public BasicAuthInterceptor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void intercept(ClientRequestContext clientRequestContext) {
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        clientRequestContext.addHeader("Authorization", basicAuth);
    }
}
