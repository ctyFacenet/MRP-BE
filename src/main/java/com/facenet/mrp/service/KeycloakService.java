package com.facenet.mrp.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KeycloakService
{
    private static final Logger log = LoggerFactory.getLogger(KeycloakService.class);

    @Value("${keycloak.approval.realm}")
    private String realm;

    private final Keycloak keycloak;

    public KeycloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public String getFullNameByUsername(String username)
    {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        List<UserRepresentation> users = usersResource.search(username, true);

        if (!users.isEmpty())
        {
            String fullName = "";
            UserRepresentation user = users.get(0);

            fullName = user.getFirstName() + " " + user.getLastName();
            return fullName;
        }
        return null;
    }
}
