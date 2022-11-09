package com.example.app.service;

import com.example.app.model.FPDetails;
import com.example.app.repository.GCPStorageRepository;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GCPStorageService {

    private static String BUCKET_NAME = "fp_bucket_i2";
    @Autowired
    private Storage storage;

    @Autowired
    GCPStorageRepository gcpStorageRepository;
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        byte[] arr = multipartFile.getBytes();
        storage.create(blobInfo, arr);
        return "File " + fileName + " has been successfully uploaded to GCP";
    }

    public String downloadFile(String fileName) {
        Bucket bucket = storage.get(BUCKET_NAME);
        Page<Blob> blobs = bucket.list();
        for (Blob blob: blobs.getValues()) {
            System.out.println("file name -> " + blob.getName());
            if (fileName.equals(blob.getName())) {
                return new String(blob.getContent());
            }
        }
        return "file not found";
    }

    public String saveData(FPDetails fpDetails) {
        FPDetails fpDetailsSaved = (FPDetails) gcpStorageRepository.save(fpDetails);
        return fpDetailsSaved.toString();
    }

    public String getDataByUpdatedBy(String updatedBy) {
        return gcpStorageRepository.findAllByUpdatedBy(updatedBy).toString();
    }
}
