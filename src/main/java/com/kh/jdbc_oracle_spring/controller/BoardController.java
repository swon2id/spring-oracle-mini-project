package com.kh.jdbc_oracle_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
public class BoardController {
    /**
     * 일반 게시판 페이지를 보여줍니다.
     * URL: /board/general
     */
    @GetMapping("general")
    public String enterGeneralBoard(Model model) {
        // 게시판 목록 데이터를 조회하여 모델에 추가
        return "thymeleaf/general_board";
    }

    /**
     * 특정 게시글을 조회합니다.
     * URL: /board/general/posts/{postId}
     */
    @GetMapping("general/post/{postId}")
    public String readGeneralPost(@PathVariable("postId") String postId, Model model) {
        System.out.println("게시글 번호 : " + postId);
        // 게시글 목록 데이터를 조회하여 모델에 추가
        return "thymeleaf/general_post";
    }

    /**
     * 게시판 검색 기능을 수행합니다.
     * URL: /board/search?term=검색어
     */
    @GetMapping("search")
    public String searchBoard(@RequestParam(name = "term", required = true) String term, Model model) {
        System.out.println("검색어 : " + term);
        model.addAttribute("term", term);

        // 추가적으로 모델에 검색어를 기반으로 연관된 게시글 데이터를 가져와 추가
        return "thymeleaf/search/board_search";
    }
}
