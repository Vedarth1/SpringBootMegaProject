package org.megaproject.chatboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    @Autowired
    private CloudinaryService cloudinaryService;

    public String upload( MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return null;
            }
            String url=cloudinaryService.upload(file, "SpringFiles");
            if(url == null) {
                return url;
            }
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
