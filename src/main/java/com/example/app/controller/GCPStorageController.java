package com.example.app.controller;

import com.example.app.model.FPDetails;
import com.example.app.service.GCPStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping ("/gcp")
public class GCPStorageController {
    @Autowired
    private GCPStorageService gcpStorageService;

    @PostMapping("/save-data")
    public String saveData(
            @RequestBody FPDetails fpDetails
    ) {
        return gcpStorageService.saveData(fpDetails);
    }

    @GetMapping ("/get-data/{updatedBy}")
    public String getData(
            @PathVariable String updatedBy
    ) {
        return gcpStorageService.getDataByUpdatedBy(updatedBy);
    }

    @PostMapping("/upload-file")
    public String uploadFile(
            @RequestParam("file") MultipartFile multipartFile
    ) throws IOException {
        return gcpStorageService.uploadFile(multipartFile);
    }

    @GetMapping ("/download-file/{fileName}")
    public String download(
            @PathVariable String fileName
    ) {
        return gcpStorageService.downloadFile(fileName);
    }
}