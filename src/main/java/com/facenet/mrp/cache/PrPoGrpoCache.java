package com.facenet.mrp.cache;

import com.facenet.mrp.domain.mrp.SapOnOrderSummary;
import com.facenet.mrp.repository.mrp.SapOnOrderSummaryRepository;
import com.facenet.mrp.service.utils.Constants;
import com.facenet.mrp.service.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PrPoGrpoCache {
    private static final Logger logger = LoggerFactory.getLogger(PrPoGrpoCache.class);
    private final Set<String> prCache = ConcurrentHashMap.newKeySet();
    private final Set<String> poCache = ConcurrentHashMap.newKeySet();
    private final Set<String> grpoCache = ConcurrentHashMap.newKeySet();


    public PrPoGrpoCache(SapOnOrderSummaryRepository sapOnOrderSummaryRepository) {
        logger.info("-Start init clone PR/PO/GRPO--");
        List<SapOnOrderSummary> openPrPoGrpoInMrp = sapOnOrderSummaryRepository.findAllByStatus("O");
        for (SapOnOrderSummary sapOnOrderSummary : openPrPoGrpoInMrp) {
            switch (sapOnOrderSummary.getType()) {
                case Constants.PR_TYPE:
                    this.addPr(sapOnOrderSummary.getSapCode(),sapOnOrderSummary.getLineNumber());
                    break;
                case Constants.PO_TYPE:
                    this.addPo(sapOnOrderSummary.getSapCode(), sapOnOrderSummary.getLineNumber());
                    break;
                case Constants.GRPO_TYPE:
                    this.addGrpo(sapOnOrderSummary.getSapCode(), sapOnOrderSummary.getLineNumber());
                    break;
            }
        }
        logger.info("--End init clone PR/PO/GRPO--");
    }

    public Set<String> getPrCache() {
        return prCache;
    }

    public Set<String> getPoCache() {
        return poCache;
    }

    public Set<String> getGrpoCache() {
        return grpoCache;
    }

    public void addPr(String docEntry, Integer lineNumber) {
        prCache.add(Utils.toItemKey(docEntry, String.valueOf(lineNumber)));
    }

    public void addPo(String docEntry, Integer lineNumber) {
        poCache.add(Utils.toItemKey(docEntry, String.valueOf(lineNumber)));
    }

    public void addGrpo(String docEntry, Integer lineNumber) {
        grpoCache.add(Utils.toItemKey(docEntry, String.valueOf(lineNumber)));
    }

    public boolean isPrContained(String docEntry, Integer lineNumber) {
        return prCache.contains(Utils.toItemKey(docEntry, String.valueOf(lineNumber)));
    }

    public boolean isPoContained(String docEntry, Integer lineNumber) {
        return poCache.contains(Utils.toItemKey(docEntry, String.valueOf(lineNumber)));
    }

    public boolean isGrpoContained(String docEntry, Integer lineNumber) {
        return grpoCache.contains(Utils.toItemKey(docEntry, String.valueOf(lineNumber)));
    }

    public void removePr(String docEntry, Integer lineNumber) {
        prCache.remove(Utils.toItemKey(docEntry, String.valueOf(lineNumber)));
    }

    public void removePo(String docEntry, Integer lineNumber) {
        poCache.remove(Utils.toItemKey(docEntry, String.valueOf(lineNumber)));
    }

    public void removeGrpo(String docEntry, Integer lineNumber) {
        grpoCache.remove(Utils.toItemKey(docEntry, String.valueOf(lineNumber)));
    }
}
