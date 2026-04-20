package com.example.demo.member.contoller;

import com.example.demo.auth.entity.EmailVerification;
import com.example.demo.auth.repository.VerificationRepository;
import com.example.demo.member.dto.MemberDto;
import com.example.demo.member.dto.MemberUpdateDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    private final VerificationRepository verificationRepository;

    @GetMapping("/signup")
    public String signup(MemberDto memberDto) {
        return "members/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberDto memberDto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "members/signup";
        }

        EmailVerification emailVerification = verificationRepository.findByEmail(memberDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 인증이 필요합니다."));

        if (!emailVerification.isVerified()) {
            throw new IllegalArgumentException("이메일 인증이 필요합니다.");
        }

        if (emailVerification.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증시간이 만료되었습니다.");
        }

        memberService.signup(memberDto);

        verificationRepository.delete(emailVerification);

        return "redirect:/";
    }

    @GetMapping("/me")
    public String member(Model model,
                         Authentication authentication) {

        Member member = memberService.findByEmail(authentication.getName());

        model.addAttribute("member", member);

        return "members/member";
    }

    @GetMapping("/me/update")
    public String memberUpdate(Model model,
                               MemberUpdateDto memberUpdateDtoDto,
                               Authentication authentication) {

        Member member = memberService.findByEmail(authentication.getName());

        model.addAttribute("member", member);

        return "members/update";
    }

    @PostMapping("/me/update")
    public String memberUpdate(@Valid MemberUpdateDto memberUpdateDto,
                               BindingResult bindingResult,
                               Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "members/update";
        }

        Member member = memberService.findByEmail(authentication.getName());

        memberService.memberUpdate(member, memberUpdateDto);

        return "redirect:/members/me";

    }

    @PostMapping("/me/password")
    public String updatePassword(@Valid MemberUpdateDto memberUpdateDto,
                                 BindingResult bindingResult,
                                 Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "members/update";
        }

        Member member = memberService.findByEmail(authentication.getName());

        memberService.updatePassword(member, memberUpdateDto);

        return "redirct:/";
    }
}
