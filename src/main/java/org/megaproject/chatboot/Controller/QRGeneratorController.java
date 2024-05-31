package org.megaproject.chatboot.Controller;
import jakarta.servlet.http.HttpServletResponse;
import org.megaproject.chatboot.Service.FileUploadService;
import org.megaproject.chatboot.Utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;

@RestController
public class QRGeneratorController {
    private static final int width=350;
    private static final int height=350;

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<byte[]> uploadFile(@RequestPart("file") MultipartFile file) throws Exception {
        try {
            String url = fileUploadService.upload(file);
            byte[] qrCodeImage = QRCodeGenerator.getQRCodeImage(url, width, height);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.IMAGE_PNG);

            return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/downloadQR")
    public void download(@RequestPart("file") MultipartFile file,HttpServletResponse response)
            throws Exception {
        String url= fileUploadService.upload(file);
        byte[] qrCodeImage = QRCodeGenerator.getQRCodeImage(url, width, height);

        response.setContentType("image/png");
        response.setHeader("Content-Disposition", "attachment; filename=QRCode.png");

        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(qrCodeImage);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
