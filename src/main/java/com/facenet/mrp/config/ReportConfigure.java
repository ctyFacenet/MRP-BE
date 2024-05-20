package com.facenet.mrp.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportConfigure {

    private final Logger log = LoggerFactory.getLogger(ReportConfigure.class);


    private final String serverUrl;
    private final String realm;
    private final String clientId;
    private final String clientSecret;
    private final String username;
    private final String password;

    public ReportConfigure(String serverUrl, String realm, String clientId, String clientSecret, String username, String password) {
        this.serverUrl = serverUrl;
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.username = username;
        this.password = password;
    }

    public String getAccessToken() {

        log.info(serverUrl);
        log.info(realm);
        log.info(clientId);
        log.info(clientSecret);
        log.info(username);
        log.info(password);
        Keycloak keycloak = KeycloakBuilder
            .builder()
            .grantType(OAuth2Constants.PASSWORD)
            .serverUrl(serverUrl)
            .realm(realm)
            .clientId(clientId)
            //            .clientSecret(clientSecret)
            .username(username)
            .password(password)
            .build();

        AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
        return tokenResponse.getToken();
    }
}
