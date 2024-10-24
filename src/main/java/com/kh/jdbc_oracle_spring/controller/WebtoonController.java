package com.kh.jdbc_oracle_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class WebtoonController {
    @GetMapping("")
    public String enterMainPage(Model model) {
        // 초기 화면에 필요한 모든 데이를 모델에 추가
        return "thymeleaf/index";
    }
    
    @GetMapping("search")
    public String searchWebtoon(@RequestParam(name = "term", required = true) String term, Model model) {
        System.out.println("검색어 : " + term);
        model.addAttribute("term", term);

        // 추가적으로 모델에 검색어를 기반으로 연관된 웹툰 데이터를 가져와 추가 + 기본적으로 추천 목록도 업데이트 해줘야됨
        return "thymeleaf/search/webtoon_search";
    }
}
