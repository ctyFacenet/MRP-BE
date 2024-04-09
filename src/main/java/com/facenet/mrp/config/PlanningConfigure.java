package com.facenet.mrp.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;

public class PlanningConfigure {

    private final String serverUrl;
    private final String realm;
    private final String clientId;
    private final String clientSecret;
    private final String username;
    private final String password;

    public PlanningConfigure(String serverUrl, String realm, String clientId, String clientSecret, String username, String password) {
        this.serverUrl = serverUrl;
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.username = username;
        this.password = password;
    }

    public String getAccessToken() {
        AccessTokenResponse tokenResponse = KeycloakBuilder
            .builder()
            .serverUrl(serverUrl)
            .realm(realm)
            .grantType(OAuth2Constants.PASSWORD)
            .clientId(clientId)
            .clientSecret(clientSecret)
            .username(username)
            .password(password)
            .build()
            .tokenManager()
            .getAccessToken();
        return tokenResponse.getToken();
    }
}
