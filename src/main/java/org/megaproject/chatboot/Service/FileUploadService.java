package org.megaproject.chatboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileUploadService {

    @Autowired
    private CloudinaryService cloudinaryService;

    public String upload( MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        return cloudinaryService.upload(file, "SpringFiles");
    }
}
