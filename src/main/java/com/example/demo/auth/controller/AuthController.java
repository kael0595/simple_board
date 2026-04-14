package com.example.demo.auth.controller;

import com.example.demo.auth.service.AuthService;
import com.example.demo.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login(MemberDto memberDto) {
        return "members/login";
    }

    @PostMapping("/email/send")
    public ResponseEntity<?> sendEmail(@RequestParam String email) {

        authService.sendVerificationCode(email);

        return ResponseEntity.ok("인증 메일 발송 완료");
    }

    @PostMapping("/email/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String email,
                                         @RequestParam String code) {

        authService.verify(email, code);

        return ResponseEntity.ok("인증 성공");
    }
}
