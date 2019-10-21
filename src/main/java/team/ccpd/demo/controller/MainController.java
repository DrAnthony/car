package team.ccpd.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import team.ccpd.demo.response.ResponseEntity;
import team.ccpd.demo.service.MainService;

@RestController
public class MainController {
    //private ResponseEntity responseEntity;
    @Autowired
    private MainService mainService;

    @RequestMapping("/upload")
    public ResponseEntity upload(@RequestParam("file")MultipartFile multipartFile){

        return mainService.upload(multipartFile);
    }
}
