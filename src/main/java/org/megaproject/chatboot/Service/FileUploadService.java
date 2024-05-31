package org.megaproject.chatboot.Service;

import org.megaproject.chatboot.Models.FileModel;
import org.megaproject.chatboot.Models.Files;
import org.megaproject.chatboot.Repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FileUploadService {
    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public ResponseEntity<Map> upload(FileModel fileModel) {
        try {
            if (fileModel.getName().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (fileModel.getFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Files image = new Files();
            image.setName(fileModel.getName());
            image.setUrl(cloudinaryService.upload(fileModel.getFile(), "SpringFiles"));
            if(image.getUrl() == null) {
                return ResponseEntity.badRequest().build();
            }
            fileUploadRepository.save(image);
            return ResponseEntity.ok().body(Map.of("url", image.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
