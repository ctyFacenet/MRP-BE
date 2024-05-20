package com.facenet.mrp.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KekcloackAdConfig {

    // Planning
    @Value("${keycloak_planning.baseUrl}")
    private String serverUrl;

    @Value("${keycloak_planning.realm}")
    private String realm;

    @Value("${keycloak_planning.clientId}")
    private String clientId;

    @Value("${keycloak_planning.username}")
    private String username;

    @Value("${keycloak_planning.password}")
    private String password;

    @Value("${keycloak_planning.clientSecret}")
    private String clientSecret;

    // Report


    @Value("${keycloak_report.baseUrl}")
    private String serverReportUrl;

    @Value("${keycloak_report.realm}")
    private String realmReport;

    @Value("${keycloak_report.clientId}")
    private String clientIdReport;

    @Value("${keycloak_report.username}")
    private String usernameReport;

    @Value("${keycloak_report.password}")
    private String passwordReport;

    @Value("${keycloak_report.clientSecret}")
    private String clientSecretReport;



    @Bean
    public Keycloak getKeycloak() {
        return KeycloakBuilder
            .builder()
            .grantType(OAuth2Constants.PASSWORD)
            .serverUrl(serverUrl)
            .realm(realm)
            .clientId(clientId)
            //            .clientSecret(clientSecret)
            .username(username)
            .password(password)
            .build();
    }

    @Bean
    public PlanningConfigure PlanningTokenProvider() {
        return new PlanningConfigure(serverUrl, realm, clientId, clientSecret, username, password);
    }

    @Bean
    public ReportConfigure ReportTokenProvider() {
        return new ReportConfigure(serverReportUrl, realmReport, clientIdReport, clientSecretReport, usernameReport, passwordReport);
    }

}
