package com.facenet.mrp.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    public String getFullNamesByUsernames(String usernames) {
        if(usernames == null || usernames.isEmpty()) return null;
        // Tách chuỗi đầu vào thành danh sách username
        List<String> usernameList = Arrays.asList(usernames.split(","));

        // Tạo danh sách để chứa các tên đầy đủ
        List<String> fullNames = new ArrayList<>();

        // Lấy realm và users resource từ Keycloak
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        // Duyệt qua từng username trong danh sách
        for (String username : usernameList) {
            username = username.trim(); // Loại bỏ khoảng trắng nếu có

            // Tìm kiếm user theo username
            List<UserRepresentation> users = usersResource.search(username, true);

            // Nếu tìm thấy user, lấy tên đầy đủ
            if (!users.isEmpty()) {
                UserRepresentation user = users.get(0);
                String fullName = user.getFirstName() + " " + user.getLastName();
                fullNames.add(fullName);
            } else {
                // Nếu không tìm thấy user, thêm chuỗi "User not found"
                fullNames.add("User not found: " + username);
            }
        }

        // Trả về chuỗi các tên đầy đủ, ngăn cách bằng dấu phẩy
        return String.join(", ", fullNames);
    }

}
