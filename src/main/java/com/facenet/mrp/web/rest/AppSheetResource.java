package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.AppSheetService;
import com.facenet.mrp.service.dto.MaterialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/app-sheet")
public class AppSheetResource {

    @Autowired
    private AppSheetService appSheetService;

    @GetMapping("/materials")
    public List<MaterialDTO> getProducts() {
        return appSheetService.getData();
    }

}
