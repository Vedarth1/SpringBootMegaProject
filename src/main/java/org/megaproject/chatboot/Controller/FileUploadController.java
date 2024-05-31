package org.megaproject.chatboot.Controller;

import org.megaproject.chatboot.Models.FileModel;
import org.megaproject.chatboot.Service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<Map> uploadFile(
            @RequestParam("name") String name,
            @RequestPart("file") MultipartFile file) {
        try {
            FileModel fileModel = new FileModel();
            fileModel.setName(name);
            fileModel.setFile(file);
            return fileUploadService.upload(fileModel);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
