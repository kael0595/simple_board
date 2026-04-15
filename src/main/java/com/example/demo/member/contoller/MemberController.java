package com.example.demo.member.contoller;

import com.example.demo.auth.entity.EmailVerification;
import com.example.demo.auth.repository.VerificationRepository;
import com.example.demo.member.dto.MemberDto;
import com.example.demo.member.dto.MemberUpdateDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/me/{nickname}")
    public String member(@PathVariable("nickname") String nickname,
                         Model model) {

        Member member = memberService.findByNickName(nickname);

        model.addAttribute("member", member);

        return "members/member";
    }

    @GetMapping("/me/{nickname}/update")
    public String memberUpdate(@PathVariable("nickname") String nickname,
                               Model model,
                               MemberUpdateDto memberUpdateDtoDto) {

        Member member = memberService.findByNickName(nickname);

        model.addAttribute("member", member);

        return "members/update";
    }

    @PostMapping("/me/{nickname}/update")
    public String memberUpdate(@PathVariable("nickname") String nickname,
                               @Valid MemberUpdateDto memberUpdateDto,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "members/update";
        }

        Member member = memberService.findByNickName(nickname);

        memberService.memberUpdate(member, memberUpdateDto);

        return "redirect:/members/me/" + nickname;

    }

    @PostMapping("/me/{nickname}/password")
    public String updatePassword(@PathVariable("nickname") String nickname,
                                 @Valid MemberUpdateDto memberUpdateDto,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "members/update";
        }

        Member member = memberService.findByNickName(nickname);

        memberService.updatePassword(member, memberUpdateDto);

        return "redirct:/";
    }
}
