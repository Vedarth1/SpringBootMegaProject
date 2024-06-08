package org.megaproject.chatboot.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.megaproject.chatboot.Service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/bot")
public class ChatwithDocumentController{

    @Autowired
    ApiService apiService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return apiService.getData();
    }

    @PostMapping("/fileupload")
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) {
        return apiService.uploadFile(file);
    }

    @PostMapping("/query")
    public ResponseEntity<String> query(@RequestBody String query) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map queryMap = mapper.readValue(query, Map.class);
        String actualQuery = (String) queryMap.get("query");
        System.out.println("Extracted query: " + actualQuery);
        return apiService.query(actualQuery);
    }
}
