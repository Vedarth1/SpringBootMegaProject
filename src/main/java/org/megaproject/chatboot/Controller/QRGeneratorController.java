package org.megaproject.chatboot.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.megaproject.chatboot.Service.FileUploadService;
import org.megaproject.chatboot.Utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/api/QR")
public class QRGeneratorController {
    private static final int width = 350;
    private static final int height = 350;

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<byte[]> uploadFile(@RequestPart("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String url = fileUploadService.upload(file);
        System.out.println(url);
        byte[] qrCodeImage = QRCodeGenerator.getQRCodeImage(url, width, height);

        request.getSession().setAttribute("uploadedFileUrl", url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.IMAGE_PNG);

        return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
    }

    @GetMapping("/downloadQR")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = (String) request.getSession().getAttribute("uploadedFileUrl");
        if (url == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("No file uploaded");
            return;
        }
        byte[] qrCodeImage = QRCodeGenerator.getQRCodeImage(url, width, height);

        response.setContentType("image/png");
        response.setHeader("Content-Disposition", "attachment; filename=QRCode.png");

        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(qrCodeImage);
            outputStream.flush();
            System.out.println("Download complete :"+url);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
