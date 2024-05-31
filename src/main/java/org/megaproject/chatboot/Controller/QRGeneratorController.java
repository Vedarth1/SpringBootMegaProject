package org.megaproject.chatboot.Controller;
import org.megaproject.chatboot.Service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public String uploadFile(
            @RequestPart("file") MultipartFile file) {
        try {
            return fileUploadService.upload(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
