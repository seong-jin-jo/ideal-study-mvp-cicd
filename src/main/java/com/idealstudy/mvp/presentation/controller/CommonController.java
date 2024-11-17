package com.idealstudy.mvp.presentation.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @GetMapping("/favicon.ico")
    public ResponseEntity<String> returnFavicon() {
        // 204 NO CONTENT
        return new ResponseEntity<>("현재 favicon은 존재하지 않음", HttpStatusCode.valueOf(204));
    }
}
