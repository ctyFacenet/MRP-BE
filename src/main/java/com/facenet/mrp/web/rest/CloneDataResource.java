package com.facenet.mrp.web.rest;

import com.facenet.mrp.thread.SyncDataFromSap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RequestMapping("/api/sync-data")
@RestController
public class CloneDataResource {
    private final SyncDataFromSap syncDataFromSap;

    public CloneDataResource(SyncDataFromSap syncDataFromSap) {
        this.syncDataFromSap = syncDataFromSap;
    }

    @GetMapping("/hold")
    public void syncData() throws ParseException {
        syncDataFromSap.syncData();
    }

    @GetMapping("/pr_po_grpo")
    public void syncPrPoGrpo() {
        syncDataFromSap.syncPrPoGrpo();
    }
}
