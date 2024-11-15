package com.idealstudy.mvp.presentation;

import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    @PostMapping("/auth/login")
    public void login() {

    }

    @PostMapping("/auth/logout")
    public void logout() {

    }

    @PostMapping("/api/users/sign-up")
    public void signUp() {

    }

    @GetMapping("/api/users/{userId}")
    public void findMember(@PathVariable String userId) {

    }

    // page number는 어떻게 표현할 생각?
    @GetMapping("/api/users")
    public void findMemberList() {

    }

    @DeleteMapping("/api/users/{userId}")
    public void deleteMember(@PathVariable String userId) {

    }

    @PatchMapping("/api/users/{userId}")
    public void updateMember(@PathVariable String userId) {

    }
}
