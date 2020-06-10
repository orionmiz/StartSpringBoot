package org.zerock.controller;

import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.domain.Member;
import org.zerock.persistence.MemberRepository;

import javax.transaction.Transactional;

@Controller
@Log
@RequestMapping("/member")
public class MemberController {

    final PasswordEncoder encoder;

    final MemberRepository repo;

    public MemberController(PasswordEncoder encoder, MemberRepository repo) {
        this.encoder = encoder;
        this.repo = repo;
    }

    @GetMapping("/join")
    public void join() {

    }


    @Transactional
    @PostMapping("/join")
    public String joinPost(@ModelAttribute("member") Member member) {
        log.info("Member: " + member);

        String encrypted = encoder.encode(member.getUpw());

        log.info("en: " + encrypted);

        member.setUpw(encrypted);

        repo.save(member);

        return "/member/joinResult";
    }

}
