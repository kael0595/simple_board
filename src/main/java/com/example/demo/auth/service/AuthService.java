package com.example.demo.auth.service;

import com.example.demo.auth.entity.EmailVerification;
import com.example.demo.auth.repository.VerificationRepository;
import com.example.demo.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmailService emailService;

    private final VerificationRepository verificationRepository;

    public void sendVerificationCode(String email) {

        String code = generateCode();

        EmailVerification emailVerification = verificationRepository
                .findByEmail(email).orElse(new EmailVerification());

        emailVerification.setCode(code);
        emailVerification.setEmail(email);
        emailVerification.setExpireTime(LocalDateTime.now().plusMinutes(5));
        emailVerification.setVerified(false);

        verificationRepository.save(emailVerification);

        emailService.sendEmail(email, code);

    }

    private String generateCode() {

        return String.valueOf((int) ((Math.random() * 900000) + 100000));
    }

    public void verify(String email, String code) {

        EmailVerification emailVerification = verificationRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("인증 요청이 없습니다."));

        if (!emailVerification.getCode().equals(code)) {
            throw new RuntimeException("인증번호가 일치하지 않습니다.");
        }

        if (emailVerification.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("인증 시간이 만료되었습니다.");
        }

        emailVerification.verify();

        emailVerification.setCode(null);

    }
}
