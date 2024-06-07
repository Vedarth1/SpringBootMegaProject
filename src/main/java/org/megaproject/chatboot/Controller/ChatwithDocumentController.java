package org.megaproject.chatboot.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.megaproject.chatboot.Service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class ChatwithDocumentController {

    @Autowired
    ApiService apiService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return apiService.getData();
    }

    @PostMapping("/fileupload")
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) {
        try {
            return apiService.uploadFile(file);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    @PostMapping("/query")
    public ResponseEntity<String> query(@RequestBody String query) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map queryMap = mapper.readValue(query, Map.class);
            String actualQuery = (String) queryMap.get("query");
            System.out.println("Extracted query: " + actualQuery);
            return apiService.query(actualQuery);
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing query: " + e.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body("Validation error: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(500).body("An error occurred: " + ex.getMessage());
    }
}
