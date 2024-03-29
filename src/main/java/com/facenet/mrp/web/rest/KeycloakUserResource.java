package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.dto.response.CommonResponse;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class KeycloakUserResource {
    private final Keycloak keycloak;

    @Value("${keycloak.approval.realm}")
    private String realm;

    @Value("${keycloak.approval.role}")
    private String role;

    public KeycloakUserResource(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @GetMapping("/approval-users")
    public CommonResponse getApprovalUsers() {
        RealmResource realmResource = keycloak.realm(realm);
        return new CommonResponse<>()
            .success()
            .data(realmResource.roles().get(role).getUserMembers());
    }
}
