package org.megaproject.chatboot.Models;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileModel {
    private String name;
    private MultipartFile file;
}