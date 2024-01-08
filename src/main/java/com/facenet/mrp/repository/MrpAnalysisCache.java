package com.facenet.mrp.repository;

import com.facenet.mrp.service.dto.AdvancedMrpDTO;
import com.facenet.mrp.service.dto.SyntheticMrpDTO;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class MrpAnalysisCache {
    private final Cache<String, AdvancedMrpDTO> mrpMap;
    private final Cache<String, SyntheticMrpDTO> syntheticResultMap;

    public MrpAnalysisCache(@Value("${cache-mrp.expire}") int expireTime) {
        mrpMap = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofMinutes((expireTime)))
                .build();

        syntheticResultMap = Caffeine.newBuilder()
            .expireAfterAccess(Duration.ofMinutes((expireTime)))
            .build();
    }

    public void putMrpResult(String sessionId, AdvancedMrpDTO data) {
        mrpMap.put(sessionId, data);
    }

    public void putSyntheticResult(String sessionId, SyntheticMrpDTO data) {
        syntheticResultMap.put(sessionId, data);
    }

    public void clearCache(String sessionId) {
        mrpMap.invalidate(sessionId);
        syntheticResultMap.invalidate(sessionId);
    }

    public AdvancedMrpDTO getMrpResult(String sessionId) {
        return mrpMap.getIfPresent(sessionId);
    }

    public SyntheticMrpDTO getSyntheticResult(String sessionId) {
        return syntheticResultMap.getIfPresent(sessionId);
    }

    public Cache<String, AdvancedMrpDTO> getMrpMap() {
        return mrpMap;
    }

    public Cache<String, SyntheticMrpDTO> getSyntheticResultMap() {
        return syntheticResultMap;
    }
}
