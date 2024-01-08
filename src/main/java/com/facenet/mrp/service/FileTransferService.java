package com.facenet.mrp.service;

import com.facenet.mrp.service.dto.AdvancedMrpDTO;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.GZIPOutputStream;

@Service
public class FileTransferService {

    @Value("${file.file-path.absolute-path}")
    private String absolutePath;

    public String writeAndZipJsonToFile( AdvancedMrpDTO data) {
        try {
            // Convert the Data object to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(data);

            System.err.println("jsonData: " + jsonData);

            // Define the file path
            String filePath = absolutePath + data.getMrpSubCode();

            // Write JSON data to a Gzip file
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                 GZIPOutputStream gzipOutputStream = new GZIPOutputStream(bufferedOutputStream)) {

                gzipOutputStream.write(jsonData.getBytes());
            }

            return "Data written to " + filePath + " and gzipped successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }

    public String decompressGzipFile(File inputFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFile);
             BufferedInputStream bis = new BufferedInputStream(fis);
             GzipCompressorInputStream gzis = new GzipCompressorInputStream(bis);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

            return bos.toString();
        }
    }

}
