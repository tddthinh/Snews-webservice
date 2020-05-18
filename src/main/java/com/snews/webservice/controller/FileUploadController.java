package com.snews.webservice.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.snews.webservice.entities.Link;
import com.snews.webservice.files.StorageFileNotFoundException;
import com.snews.webservice.service.StorageService;

//@RestController
//@RequestMapping(path = "/files")
public class FileUploadController {
	
    private final StorageService storageService;
    @Autowired
    Environment environment;
    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public List<Object> listUploadedFiles(Model model) throws IOException {

        return storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList());
    }

    @GetMapping("/load/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload")
    public Link handleFileUpload(@RequestParam("file") MultipartFile file) {
    	
        storageService.store(file);
        String fileName = file.getOriginalFilename();
        String path = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                "serveFile", storageService.load(fileName).getFileName().toString()).build().toString();
        return new Link(path);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
