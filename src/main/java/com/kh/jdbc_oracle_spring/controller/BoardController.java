package com.kh.jdbc_oracle_spring.controller;

import com.kh.jdbc_oracle_spring.common.Path;
import com.kh.jdbc_oracle_spring.common.PostUtility;
import com.kh.jdbc_oracle_spring.common.ReplyUtility;
import com.kh.jdbc_oracle_spring.vo.PostVo;
import com.kh.jdbc_oracle_spring.vo.ReplyVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @GetMapping("notice")
    public String enterNoticeBoard(Model model) {
        // dao 결과 리턴 값 받기
        // 20개 단위로 1페이지 분량만 시연할 것
        List<PostVo> posts = new ArrayList<>();
        posts.add(new PostVo(1, "제목1", "내용내용", Date.valueOf("2024-10-25"), 1, 0));
        posts.add(new PostVo(2, "제목2", "내용내용", Date.valueOf("2024-10-25"), 2, 0));
        posts.add(new PostVo(3, "제목3", "내용내용", Date.valueOf("2024-10-25"), 3, 0));
        posts.add(new PostVo(4, "제목4", "내용내용", Date.valueOf("2024-10-25"), 4, 0));
        posts.add(new PostVo(5, "제목5", "내용내용", Date.valueOf("2024-10-25"), 5, 0));
        posts.add(new PostVo(6, "제목6", "내용내용", Date.valueOf("2024-10-25"), 6, 0));
        posts.add(new PostVo(7, "제목7", "내용내용", Date.valueOf("2024-10-25"), 7, 0));

        model.addAttribute("boardName", "공지게시판");
        model.addAttribute("posts", PostUtility.setPostsWithMemberNameAndUrl(posts));
        addHeaderAttribute(model);
        return "thymeleaf/board";
    }

    @GetMapping("notice/post/{postId}")
    public String readNoticePost(@PathVariable("postId") String postId, Model model) {
        // dao 결과 리턴 값 받기
        List<PostVo> posts = new ArrayList<>();
        posts.add(new PostVo(1, "제목입니다.", "내용내용", Date.valueOf("2024-10-25"), 1, 0));
        PostVo post = PostUtility.setPostsWithMemberNameAndUrl(posts).get(0);

        List<ReplyVo> replys = new ArrayList<>();
        replys.add(new ReplyVo(1, "댓글내용", Date.valueOf("2024-10-01"), 0, 0, 1, 1));
        replys.add(new ReplyVo(2, "댓글내용", Date.valueOf("2024-10-01"), 0, 0, 2, 1));
        replys.add(new ReplyVo(3, "댓글내용", Date.valueOf("2024-10-01"), 0, 0, 3, 1));

        model.addAttribute("boardName", "게시판 > 공지게시판");
        model.addAttribute("postTitle", post.getPostTitle());
        model.addAttribute("postAuthorName", post.getPostAuthorName());
        model.addAttribute("postContent", post.getPostContent());
        model.addAttribute("postPublishedDate", post.getPostPublishedDate());
        model.addAttribute("postVisit", post.getPostVisit());
        model.addAttribute("replys", ReplyUtility.setReplysWithMemberName(replys));
        addHeaderAttribute(model);
        return "thymeleaf/post";
    }

    @GetMapping("general")
    public String enterGeneralBoard(Model model) {
        // dao 결과 리턴 값 받기
        // 20개 단위로 1페이지 분량만 시연할 것
        List<PostVo> posts = new ArrayList<>();
        posts.add(new PostVo(1, "제목입니다.", "내용내용", Date.valueOf("2024-10-25"), 1, 1));


        model.addAttribute("boardName", "자유게시판");
        model.addAttribute("posts", PostUtility.setPostsWithMemberNameAndUrl(posts));
        addHeaderAttribute(model);
        return "thymeleaf/board";
    }

    @GetMapping("general/post/{postId}")
    public String readGeneralPost(@PathVariable("postId") String postId, Model model) {
        // dao 결과 리턴 값 받기
        List<PostVo> posts = new ArrayList<>();
        posts.add(new PostVo(1, "제목입니다.", "내용내용", Date.valueOf("2024-10-25"), 1, 1));
        PostVo post = PostUtility.setPostsWithMemberNameAndUrl(posts).get(0);

        List<ReplyVo> replys = new ArrayList<>();
        replys.add(new ReplyVo(1, "댓글내용", Date.valueOf("2024-10-01"), 0, 0, 1, 1));
        replys.add(new ReplyVo(2, "댓글내용", Date.valueOf("2024-10-01"), 0, 0, 2, 1));
        replys.add(new ReplyVo(3, "댓글내용", Date.valueOf("2024-10-01"), 0, 0, 3, 1));

        model.addAttribute("boardName", "게시판 > 자유게시판");
        model.addAttribute("postTitle", post.getPostTitle());
        model.addAttribute("postAuthorName", post.getPostAuthorName());
        model.addAttribute("postContent", post.getPostContent());
        model.addAttribute("postPublishedDate", post.getPostPublishedDate());
        model.addAttribute("postVisit", post.getPostVisit());
        model.addAttribute("replys", ReplyUtility.setReplysWithMemberName(replys));
        addHeaderAttribute(model);
        return "thymeleaf/post";
    }

    @GetMapping("search")
    public String searchBoard(@RequestParam(name = "term", required = true) String term, Model model) {
        System.out.println("검색어 : " + term);
        model.addAttribute("term", term);
        addHeaderAttribute(model);
        return "thymeleaf/search/board_search";
    }

    private void addHeaderAttribute(Model model) {
        model.addAttribute("logoText", "KH TOON 커뮤니티");
        model.addAttribute("toggleServiceName", "웹툰");
        model.addAttribute("toggleServicePagePath", Path.WEBTOON_PAGE);
        model.addAttribute("serviceMainPagePath", Path.GENERAL_BOARD_PAGE);
        model.addAttribute("noticeBoardPagePath", Path.NOTICE_BOARD_PAGE);
    }
}
