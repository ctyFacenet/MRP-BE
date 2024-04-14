package com.facenet.mrp.web.rest;


import com.facenet.mrp.service.ColumnPropertyService;
import com.facenet.mrp.service.dto.KeyDictionaryDTO;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

@RestController
@RequestMapping("api/column-properties")
public class ColumnPropertyResource {

    @Autowired
    ColumnPropertyService columnPropertyService;

    @PostMapping("")
    public CommonResponse createProperties(@RequestBody KeyDictionaryDTO input) {
        columnPropertyService.createProperty(input);
        return new CommonResponse<>().success();
    }

    @PutMapping("/{keyName}")
    public CommonResponse updateProperties(@PathVariable String keyName, @RequestBody KeyDictionaryDTO input) {
        columnPropertyService.updateProperty(keyName, input, input.getEntityType());
        return new CommonResponse<>().success();
    }

    @DeleteMapping("/{keyName}/{entityType}")
    public CommonResponse deleteProperties(@PathVariable String keyName, @PathVariable Integer entityType) {
        columnPropertyService.deleteProperty(keyName, entityType);
        return new CommonResponse<>().success();
    }

    @GetMapping("/{entityType}")
    public CommonResponse getColumnForEntity(@PathVariable Integer entityType) {
        return columnPropertyService.getColumnList(entityType);
    }

    @PostMapping("/import-column/{entityType}")
    public CommonResponse importColumn(@RequestParam("file") MultipartFile file, @PathVariable String entityType)
        throws IOException, ParseException {
        return columnPropertyService.importColumn(file, entityType);
    }

    @PostMapping("/search")
    public CommonResponse search(@RequestBody PageFilterInput<KeyDictionaryDTO> input) {
        Pageable pageable = Pageable.unpaged();
        if (input.getPageSize() != 0) {
            pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        }
        return new CommonResponse().success().data(columnPropertyService.search(input.getFilter(), input.getCommon(), pageable));
    }

    @PostMapping("/common-autocomplete")
    public CommonResponse<Set<String>> autocompleteCommonConfig(@RequestBody PageFilterInput<KeyDictionaryDTO> input) {
        Pageable pageable = Pageable.unpaged();
        if (input.getPageSize() != 0) {
            pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        }
        return new CommonResponse()
            .success()
            .data(columnPropertyService.autocompleteCommonConfig(input.getFilter(), input.getCommon(), pageable));
    }
}
