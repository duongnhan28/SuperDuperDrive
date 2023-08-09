package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/addFile")
    public String addFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) throws IOException {
        String uploadError = null;

        User user = new User();
        user = userService.getUser(authentication.getName());

        if (fileUpload.isEmpty()) {
            uploadError = "Please select a non-empty file.";
            return"redirect:/home";
        }

        if (!fileService.isExsis(fileUpload.getOriginalFilename(), user.getUserId())) {
            uploadError = "The file already exists.";
            return"redirect:/home";

        }

        if(uploadError!=null) {
            model.addAttribute("error",uploadError);
        }else {
            model.addAttribute("success","File uploaded successfully!");
        }

        fileService.addFile(fileUpload, user.getUserId());
        //return"/home";
        return"redirect:/home";
    }

    @GetMapping("deleteFile/{id}")
    public String delelteFile(@PathVariable Integer id){
        fileService.deleteFile(id);
        return"redirect:/home";
    }

    @GetMapping("downloadFile/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer id){
        File file = fileService.getFileById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ file.getFileName()+"\"")
                .body(new ByteArrayResource(file.getFileData()));
    }


}



