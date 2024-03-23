package com.facenet.mrp.web.rest;

import com.facenet.mrp.service.FileTransferService;
import com.facenet.mrp.service.MrpAdvancedAnalysisServiceV2;
import com.facenet.mrp.service.MrpAdvancedAnalysisServiceV3;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import com.facenet.mrp.service.dto.request.AnalyticsPaginatedRequest;
import com.facenet.mrp.service.dto.request.AnalyticsSearchRequest;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.model.RequestInput;
import com.facenet.mrp.thread.CloneBomService;
import com.facenet.mrp.service.MrpBasicAnalysisService;
import com.facenet.mrp.service.dto.AdvancedMrpDTO;
import com.facenet.mrp.service.dto.mrp.CurrentScheduleTimeInput;
import com.facenet.mrp.service.dto.mrp.MrpAnalyticsInput;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/order-analytics")
public class MrpAnalyticsResource {

    private static final Logger log = LogManager.getLogger(MrpAnalyticsResource.class);

    private final MrpBasicAnalysisService basicAnalysisService;
    private final MrpAdvancedAnalysisServiceV2 advancedAnalysisService;
    private final MrpAdvancedAnalysisServiceV3 advancedAnalysisServiceV3;
    private final CloneBomService cloneBomService;

    private final FileTransferService fileTransferService;

    @Value("${file.file-path.absolute-path}")
    private String absolutePath;

    public MrpAnalyticsResource(MrpBasicAnalysisService basicAnalysisService, MrpAdvancedAnalysisServiceV2 advancedAnalysisService, MrpAdvancedAnalysisServiceV3 advancedAnalysisServiceV3, CloneBomService cloneBomService, FileTransferService fileTransferService) {
        this.basicAnalysisService = basicAnalysisService;
        this.advancedAnalysisService = advancedAnalysisService;
        this.advancedAnalysisServiceV3 = advancedAnalysisServiceV3;
        this.cloneBomService = cloneBomService;
        this.fileTransferService = fileTransferService;
    }

    //TODO
    @PostMapping("/mrp-analytics")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public ResponseEntity mrpAnalysis (@RequestBody MrpAnalyticsInput input) throws ParseException {
        AdvancedMrpDTO mrpAnalyticsResult = new AdvancedMrpDTO();
        if (input.getAnalysisMode().equalsIgnoreCase("phân tích cơ bản")){
            mrpAnalyticsResult = basicAnalysisService.basicAnalysisMRP(input);
        } else if (input.getAnalysisMode().equalsIgnoreCase("phân tích nâng cao")) {
            mrpAnalyticsResult = advancedAnalysisService.estimatedProductionSchedule(input);
        }
        return ResponseEntity.ok(new CommonResponse<AdvancedMrpDTO>()
            .isOk(true)
            .message("Success")
            .errorCode("00")
            .data(mrpAnalyticsResult)
        );
    }


    //TODO
    @PostMapping("/mrp-analytics/advanced-analysis-results")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public ResponseEntity advancedMrpAnalytic(@RequestBody AnalyticsPaginatedRequest input) throws ParseException {
        AdvancedMrpDTO mrpAnalyticsResult = advancedAnalysisServiceV3.pagingResult(input.getAdvancedMrpDTO(), input.getPageNumber(), input.getPageSize());
        return ResponseEntity.ok(new CommonResponse<AdvancedMrpDTO>()
            .isOk(true)
            .message("Success")
            .errorCode("00")
            .data(mrpAnalyticsResult)
        );
    }

    @PostMapping("/mrp-analytics/advanced-analysis-results/next-page/{ssId}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public ResponseEntity advancedMrpAnalyticNextPage(@PathVariable String ssId ,@RequestBody RequestInput<AnalyticsSearchRequest> input) throws ParseException {
        AdvancedMrpDTO mrpAnalyticsResult = advancedAnalysisServiceV3.pagingCache(ssId, input.getPageNumber(), input.getPageSize(), input.getFilter());
        return ResponseEntity.ok(new CommonResponse<AdvancedMrpDTO>()
            .isOk(true)
            .message("Success")
            .errorCode("00")
            .data(mrpAnalyticsResult)
        );
    }

    @PostMapping("/mrp-analytics/exact-production-time")
    public ResponseEntity choseExactTimeAdvancedMrpAnalytic(@RequestBody CurrentScheduleTimeInput input) throws ParseException {
        List<String> listTimeResult = advancedAnalysisService.choseExactAnalyticTime(input);
        return ResponseEntity.ok(new CommonResponse<AdvancedMrpDTO>()
            .isOk(true)
            .message("Success")
            .errorCode("00")
            .data(listTimeResult)
        );
    }

    @PostMapping("/mrp-analytics/import-estimated-production-schedule")
    public ResponseEntity importExcelSchesule(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
        return ResponseEntity.ok(new CommonResponse<>()
            .isOk(true)
            .message("Success")
            .errorCode("00")
            .data( advancedAnalysisService.mrpAdvancedImportExcel(file)));
    }

    @PostMapping("/mrp-analytics/save-json-response")
    public ResponseEntity saveJsonResponse(@RequestBody AdvancedMrpDTO data) throws IOException {

        return ResponseEntity.ok(new CommonResponse<>()
            .isOk(true)
            .message("Success")
            .errorCode("00")
            .data(fileTransferService.writeAndZipJsonToFile(data)));
    }

    @PostMapping("/mrp-analytics/unzip")
    public ResponseEntity saveJsonResponse(@RequestParam("file") MultipartFile file) throws IOException {

        // Create a temporary file to store the uploaded data
        File tempFile = File.createTempFile("upload-", ".tmp");
        file.transferTo(tempFile);

        // Decompress the file and return the content
        String decompressedContent = fileTransferService.decompressGzipFile(tempFile);

        ObjectMapper objectMapper = new ObjectMapper();
        AdvancedMrpDTO data = objectMapper.readValue(decompressedContent, AdvancedMrpDTO.class);

        // Delete the temporary file
        tempFile.delete();

        return ResponseEntity.ok(new CommonResponse<>()
            .isOk(true)
            .message("Success")
            .errorCode("00")
            .data(data));
    }

    @GetMapping("mrp-analytics/download/{fileName}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable String fileName) {
        String filePath = absolutePath + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            // Handle the case if the file does not exist
            return ResponseEntity.notFound().build();
        }

        // Set the appropriate Content-Disposition header to trigger download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());
        headers.set(HttpHeaders.CONTENT_ENCODING, "gzip");
        // Set the Content-Type based on the file type, or you can set it to "application/octet-stream" for generic binary data.
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
    }
}
