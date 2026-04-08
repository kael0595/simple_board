package com.example.demo.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String email, String code) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom("ants7021@naver.com");
        mailMessage.setSubject("인증번호입니다.");
        mailMessage.setText("인증번호 : " + code);

        javaMailSender.send(mailMessage);

    }
}
