package com.project.blog.services.impl;

import com.project.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        String actualFileName = file.getOriginalFilename();

        String randomID = UUID.randomUUID().toString();
        assert actualFileName != null;
        String fileName = randomID.concat(actualFileName.substring(actualFileName.lastIndexOf(".")));


        String filePath = path + File.separator + fileName;

        File f = new File(path);
        if(!f.exists()){
            boolean dirCreated = f.mkdirs();
            if(!dirCreated){
                throw new IOException("Failed to create directory at " + path);
            }
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;

    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }
}
