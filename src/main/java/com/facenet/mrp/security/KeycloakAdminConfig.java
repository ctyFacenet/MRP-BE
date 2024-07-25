package com.facenet.mrp.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminConfig {
    private static final Logger logger = LogManager.getLogger(KeycloakAdminConfig.class);

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.credentials.username}")
    private String username;

    @Value("${keycloak.credentials.password}")
    private String password;

    @Bean
    public Keycloak getKeycloak() {
        logger.info(realm + clientId + username + password);
        return KeycloakBuilder.builder()
            .grantType(OAuth2Constants.PASSWORD)
            .serverUrl(serverUrl)
            .realm(realm)
            .clientId(clientId)
//            .clientSecret(clientSecret)
            .username(username)
            .password(password)

            .build();
    }
}
