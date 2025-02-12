package com.edu.book.api.http.controller.upload;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RestController
@RequestMapping("/v2/upload")
public class DeepSeekUploadController {

    private static final String UPLOAD_DIR = "uploads";

    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 创建上传目录（如果不存在）
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 生成唯一的文件名
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File filePath = new File(uploadDir, fileName);

            // 复制文件内容到目标路径
            InputStream in = file.getInputStream();
            OutputStream out = new FileOutputStream(filePath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.close();

            return "File uploaded successfully: " + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

//    @GetMapping("{fileName}")
//    public void downloadFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
//        Path filePath = Path.of(UPLOAD_DIR).resolve(fileName);
//
//        if (Files.exists(filePath)) {
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//
//            Files.copy(filePath, response.getOutputStream());
//            response.flushBuffer();
//        } else {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
//        }
//    }

}
