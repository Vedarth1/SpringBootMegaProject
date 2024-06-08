package org.megaproject.chatboot.Service;

import com.cloudinary.Cloudinary;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {
    @Resource
    private Cloudinary cloudinary;

    public String upload(MultipartFile file, String Folder) throws IOException {
        HashMap<Object, Object> options = new HashMap<>();
        options.put("folder", Folder);
        options.put("resource_type", "auto");
        Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
        System.out.println("Upload Result: " + uploadedFile);
        return (String) uploadedFile.get("secure_url");
    }
}