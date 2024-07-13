package com.conte.hackothumun.service;

import com.conte.hackothumun.entity.Event;
import com.conte.hackothumun.entity.FileApp;
import com.conte.hackothumun.repository.FileAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;
    private final FileAppRepository fileRepository;

    @Autowired
    public FileStorageService(FileAppRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

        try{
            Files.createDirectories(this.fileStorageLocation);
        }catch (Exception e){
            throw new RuntimeException("Could not create the directory at " + this.fileStorageLocation, e);
        }
    }

    public FileApp storeFile(MultipartFile file, Event event) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(fileName.contains("..")){
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(),targetLocation,StandardCopyOption.REPLACE_EXISTING);

            FileApp dbFileApp = new FileApp();
            dbFileApp.setFileName(fileName);
            dbFileApp.setFileType(file.getContentType());
            dbFileApp.setFilePath(targetLocation.toString());
            dbFileApp.setSize(file.getSize());
            dbFileApp.setEvent(event);
            return fileRepository.save(dbFileApp);

        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + fileName, e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()){
                return resource;
            }else{
                throw new RuntimeException("File not found " + fileName);
            }
        }catch (MalformedURLException e){
            throw new RuntimeException("Could not load file " + fileName, e);
        }
    }

}
