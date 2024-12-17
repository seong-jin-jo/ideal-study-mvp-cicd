package com.idealstudy.mvp.presentation.controller.classroom.inclass;

import com.idealstudy.mvp.security.annotation.ForStudent;
import com.idealstudy.mvp.security.annotation.ForTeacher;
import com.idealstudy.mvp.security.annotation.ForUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AttendanceController {

    @ForStudent
    @PostMapping("/api/attendance/check-in")
    public void checkIn() {

    }

    @ForStudent
    @PostMapping("/api/attendance/check-out")
    public void checkOut() {

    }

    @ForUser
    @GetMapping("/api/attendance/student/{studentId}")
    public void getIndividualAttendance(@PathVariable String studentId) {


    }

    @ForTeacher
    @GetMapping("/api/attendance/{classroomId}/{date}")
    public void getTodayClassroomAttendance(@PathVariable String classroomId,
                                        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {


    }
}
