package com.example.demo.auth.service;

import com.example.demo.auth.repository.VerificationRepository;
import com.example.demo.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmailService emailService;

    private final VerificationRepository verificationRepository;

    public void sendVerificationCode(String email) {

        String code = generateCode();

        verificationRepository.save(email, code);

        emailService.sendEmail(email, code);

    }

    private String generateCode() {

        return String.valueOf((int) ((Math.random() * 900000) + 100000));
    }

    public void verifyCode(String email, String code) {

        String saveCode = verificationRepository.findByEmail(email);

        if(saveCode == null) {
            throw new RuntimeException("인증 요청 없음");
        }

        if (!saveCode.equals(code)) {
            throw new RuntimeException("인증번호 불일치");
        }

        verificationRepository.remove(email);

    }
}
