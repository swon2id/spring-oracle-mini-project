package com.kh.jdbc_oracle_spring.controller;

import com.kh.jdbc_oracle_spring.JdbcOracleSpringApplication;
import com.kh.jdbc_oracle_spring.dao.MemberDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberDao memberDao;

    public MemberController(MemberDao memberDao) { this.memberDao = memberDao; }

    @GetMapping("login")
    public String enterLoginPage() {
        return "thymeleaf/login";
    }

    @PostMapping("login/Authentication")
    public String login(@RequestParam("id") String id, @RequestParam("password") String password) {
        // ID와 Password로 처리 로직 수행
        System.out.println("ID: " + id);
        System.out.println("Password: " + password);
        JdbcOracleSpringApplication.currMemberNum = memberDao.selectById(id).getMemberNum();
        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout() {
        JdbcOracleSpringApplication.currMemberNum = null;
        return "redirect:/";
    }
}
