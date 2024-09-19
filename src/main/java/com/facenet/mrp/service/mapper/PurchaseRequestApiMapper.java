package com.facenet.mrp.service.mapper;

import com.facenet.mrp.domain.mrp.PurchaseHasRecommendationEntity;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationEntity;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.dto.sap.PurchaseRequestApiDTO;
import com.facenet.mrp.service.dto.sap.PurchaseRequestDetailApiDTO;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseRequestApiMapper {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Autowired
    private Keycloak keycloakAdminClient;

    @Value("${keycloak.approval.realm}")
    private String realm;
    public PurchaseRequestApiDTO toDTO(PurchaseRecommendationEntity purchaseRecommendationEntity, List<PurchaseRequestDetailApiDTO> plans, PurchaseHasRecommendationEntity purchaseHasRecommendationEntity) {
        PurchaseRequestApiDTO purchaseRequestDTO = new PurchaseRequestApiDTO();
        purchaseRequestDTO.setType("PR");
        purchaseRequestDTO.setUser(getUserAttribute(getLoggedInUserId(), "user"));
        purchaseRequestDTO.setTimeRequestPR(simpleDateFormat.format(purchaseHasRecommendationEntity.getDueDate()));
        purchaseRequestDTO.setRemark(purchaseHasRecommendationEntity.getNote());
        purchaseRequestDTO.setMrpSubCode(purchaseRecommendationEntity.getMrpSubCode());
        purchaseRequestDTO.setSoCode(purchaseRecommendationEntity.getMrpPoId());
        purchaseRequestDTO.setCreateTime(simpleDateFormat.format(new Date()));
        purchaseRequestDTO.setTimeRequest(purchaseRequestDTO.getTimeRequestPR());
        purchaseRequestDTO.setTimeApproved(purchaseRequestDTO.getCreateTime());
        purchaseRequestDTO.setStatus("");

        purchaseRequestDTO.setPurchaseRecommendationDetails(plans);
        return purchaseRequestDTO;
    }

    private String getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getSubject();
        }
        return null;
    }

    private String getUserAttribute(String userId, String attributeName) {
        // Fetch the user representation from Keycloak
        UserRepresentation userRepresentation = keycloakAdminClient.realm(realm)
            .users()
            .get(userId)
            .toRepresentation();

        Map<String, List<String>> attributes = userRepresentation.getAttributes();

        if (attributes != null && attributes.containsKey(attributeName)) {
            return attributes.get(attributeName).get(0);
        }
        return null;
    }
}
