package org.megaproject.chatboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ApiService {
    @Autowired
    RestTemplate restTemplate;

    private String userSessionId;

    public ResponseEntity<String> getData() {
        String url = "http://localhost:5000/api/hello";
        return restTemplate.getForEntity(url, String.class);
    }

    public ResponseEntity<String> uploadFile(MultipartFile file) {
        String url = "http://localhost:5000/api/upload";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            userSessionId = response.getHeaders().getFirst("Set-Cookie");
        }
        return response;
    }

    public ResponseEntity<String> query(String query) {
        String url = "http://localhost:5000/api/query";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Add session ID to headers if available
        if (userSessionId != null) {
            headers.add("Cookie", userSessionId);
        }

        String requestBody = String.format("{\"query\": \"%s\"}", query);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
