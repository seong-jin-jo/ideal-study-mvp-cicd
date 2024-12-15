package com.idealstudy.mvp.presentation.controller.classroom.inclass;

import com.idealstudy.mvp.application.service.classroom.inclass.MaterialsService;
import com.idealstudy.mvp.security.annotation.ForStudent;
import com.idealstudy.mvp.security.annotation.ForTeacher;
import com.idealstudy.mvp.security.annotation.ForUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MaterialsController {

    @Autowired
    private final MaterialsService materialsService;

    @ForTeacher
    @PostMapping("/api/materials")
    public void upload() {


    }

    @ForUser
    @GetMapping("/api/materials/{materialId}")
    public void getDetails(@PathVariable Long materialId) {

    }

    @ForStudent
    @GetMapping("/api/materials/student/{studentId}")
    public void getListForStudent(@PathVariable String studentId) {

    }

    @GetMapping("/materials/classroom/{classroomId}")
    public void getListByClassroom(@PathVariable String classroomId) {

    }

    @ForTeacher
    @GetMapping("/api/materials/teacher/{teacherId}")
    public void getListForTeacher(@PathVariable String teacherId) {

    }

    @ForTeacher
    @PatchMapping("/api/materials/{materialId}")
    public void update(@PathVariable Long materialId) {

    }

    @ForTeacher
    @DeleteMapping("/api/materials/{materialId}")
    public void delete(@PathVariable Long materialId) {

    }
}
