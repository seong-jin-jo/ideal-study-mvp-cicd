package com.idealstudy.mvp.presentation.controller.classroom.preclass;

import com.idealstudy.mvp.application.service.classroom.preclass.ClassInquiryService;
import com.idealstudy.mvp.security.annotation.ForUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ClassInquiryController {

    @Autowired
    private final ClassInquiryService classInquiryService;

    @ForUser
    @PostMapping("/api/inquiries")
    public void create() {

    }

    @GetMapping("/inquiries/classes/{classId}")
    public void findListByClassId(@PathVariable String classId) {

    }

    @ForUser
    @GetMapping("/api/inquiries/users/{userId}")
    public void findListByMemberId(@PathVariable String userId) {

    }

    @GetMapping("/inquires/{inquiryId}")
    public void findDetail(@PathVariable Long inquiryId) {

    }

    @ForUser
    @PutMapping("/api/inquiries/{inquiryId}")
    public void update(@PathVariable Long inquiryId) {

    }

    @ForUser
    @DeleteMapping("/api/inquiries/{inquiryId}")
    public void delete(@PathVariable Long inquiryId) {

        
    }
}
