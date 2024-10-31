package com.kh.jdbc_oracle_spring.controller;

import com.kh.jdbc_oracle_spring.JdbcOracleSpringApplication;
import com.kh.jdbc_oracle_spring.common.*;
import com.kh.jdbc_oracle_spring.dao.MemberDao;
import com.kh.jdbc_oracle_spring.dao.PostDao;
import com.kh.jdbc_oracle_spring.dao.ReplyDao;
import com.kh.jdbc_oracle_spring.dao.ReplyEvaluationDao;
import com.kh.jdbc_oracle_spring.vo.PostVo;
import com.kh.jdbc_oracle_spring.vo.ReplyEvaluationVo;
import com.kh.jdbc_oracle_spring.vo.ReplyVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    private final MemberDao memberDao;
    private final PostDao postDao;
    private final ReplyDao replyDao;
    private final ReplyEvaluationDao replyEvaluationDao;

    public BoardController(MemberDao memberDao, PostDao postDao, ReplyDao replyDao, ReplyEvaluationDao replyEvaluationDao) {
        this.memberDao = memberDao;
        this.postDao = postDao;
        this.replyDao = replyDao;
        this.replyEvaluationDao = replyEvaluationDao;
    }

    @GetMapping("{boardType}")
    public String initBoardMainPage(
            @PathVariable("boardType") String boardType,
            Model model
    ) {
        // 없는 게시판 유형인 경우 기본(자유) 게시판으로 리다이렉션
        if (!BoardUtility.isValidBoardType(boardType)) return "redirect:/board/general";

        // POST 목록을 model에 전달
        List<PostVo> posts = postDao.selectWithMemberNameByBoardNum(BoardUtility.getBoardNum(boardType));
        model.addAttribute("boardName", BoardUtility.getBoardName(boardType));
        model.addAttribute("posts", posts);
        model.addAttribute("postUrls", PostUtility.getPostUrl(posts));

        // 게시글 작성 권한 boolean 값 전달
        model.addAttribute("isPostWritableMember",
            MemberUtility.isLoggedIn() &&
            PostUtility.isPostWritableMember(boardType, memberDao.selectMemberTypeNumByMemberNum(JdbcOracleSpringApplication.currMemberNum))
        );

        // 게시판 유형에 따라 게시글 작성 경로 전달
        // 게시글 작성 권한 boolean 값이 true인 경우 유효
        model.addAttribute("writePostUrl", boardType + "/write-post");

        // 헤더 렌더링에 필요한 값 전달
        addAttributeToHeader(model);
        return "thymeleaf/board";
    }

    @GetMapping("{boardType}/post/{postNum}")
    public String readPost(
            @PathVariable("boardType") String boardType,
            @PathVariable("postNum") int postNum,
            Model model
    ) {
        // 없는 게시판 유형인 경우 기본(자유) 게시판으로 리다이렉션
        if (!BoardUtility.isValidBoardType(boardType)) return "redirect:/board/general";

        // 현재 POST 데이터 하나를 model에 전달
        postDao.increaseVisitCountByPostNum(postNum);
        PostVo post = postDao.selectPostWithMemberNameByPostNum(postNum);
        model.addAttribute("boardName", "게시판 > " + BoardUtility.getBoardName(boardType));
        model.addAttribute("postTitle", post.getPostTitle());
        model.addAttribute("postAuthorName", post.getMemberNickname());
        model.addAttribute("postContent", post.getPostContent());
        model.addAttribute("postPublishedDate", post.getPostPublishedDate());
        model.addAttribute("postVisit", post.getPostVisit());
        model.addAttribute("isNoticeBoard", boardType.equals("notice"));
        model.addAttribute("postNum", postNum);

        // 자유게시판인 경우 댓글 데이터도 model에 전달
        if (boardType.equals("general")) {
            List<ReplyVo> replys = replyDao.selectWithMemberNicknameByPostNum(postNum);
            model.addAttribute("replys", replys);
            model.addAttribute("memberNum", JdbcOracleSpringApplication.currMemberNum);

            // 댓글 작성 권한 boolean 값
            model.addAttribute("isLoggedIn", MemberUtility.isLoggedIn());

            // 현재 유저가 작성한 댓글 목록 리스트 전달 -> 자신 댓글 삭제 용도
            model.addAttribute("replyNumListOfCurrMember", MemberUtility.isLoggedIn()
                ? replyDao.selectReplyNumByMemberNum(JdbcOracleSpringApplication.currMemberNum)
                : new ArrayList<>()
            );
        } else {
            model.addAttribute("replys", new ArrayList<>());
        }

        // 헤더 렌더링에 필요한 값 전달
        addAttributeToHeader(model);
        return "thymeleaf/post";
    }

    @GetMapping("{boardType}/write-post")
    public String writePost(
        @PathVariable("boardType") String boardType,
        Model model
    ) {
        // 1) 없는 게시판 유형이거나
        // 2) 로그인 상태가 아니거나
        // 3) 현재 공지게시글 작성 페이지고 일반 유저가 진입한 경우
        if (
            !BoardUtility.isValidBoardType(boardType) ||
            !MemberUtility.isLoggedIn() ||
            !PostUtility.isPostWritableMember(boardType, memberDao.selectMemberTypeNumByMemberNum(JdbcOracleSpringApplication.currMemberNum))
        ) return "redirect:/board/general";

        // 페이지에 렌더링할 텍스트 전달
        model.addAttribute("boardName", BoardUtility.getBoardName(boardType));

        // 헤더 렌더링에 필요한 값 전달
        addAttributeToHeader(model);
        return "thymeleaf/write-post";
    }

    @PostMapping("{boardType}/write-post/submit")
    public String registPost(
        @PathVariable("boardType") String boardType,
        @RequestParam("postTitle") String postTitle,
        @RequestParam("postContent") String postContent
    ) {
        // 1) 없는 게시판 유형이거나
        // 2) 로그인 상태가 아니거나
        // 3) 현재 공지게시글 작성 페이지고 일반 유저가 진입한 경우
        // 4) INSERT 하기 위한 데이터가 전달되지 않은 경우 (제목, 내용)
        if (
            !BoardUtility.isValidBoardType(boardType) ||
            !MemberUtility.isLoggedIn() ||
            !PostUtility.isPostWritableMember(boardType, memberDao.selectMemberTypeNumByMemberNum(JdbcOracleSpringApplication.currMemberNum)) ||
            postTitle == null || postTitle.isEmpty() ||
            postContent == null || postContent.isEmpty()
        ) return "redirect:/board/general";

        // POST INSERT 처리
        postDao.insert(new PostVo(postTitle, postContent, JdbcOracleSpringApplication.currMemberNum, BoardUtility.getBoardNum(boardType)));
        return "redirect:/board/" + boardType;
    }

    @GetMapping("search")
    public String searchPost(
            @RequestParam(name = "term", required = true) String term,
            Model model
    ) {
        // 검색어를 기반으로 POST 목록 SELECT
        List<PostVo> posts = postDao.selectWithMemberNameByTerm(term);

        // 검색 결과를 보여주기 위한 데이터, POST 목록, POST로 연결하기 위한 URL 전달
        model.addAttribute("term", term);
        model.addAttribute("posts", posts);
        model.addAttribute("postUrls", PostUtility.getPostUrl(posts));

        // 헤더 렌더링에 필요한 값 전달
        addAttributeToHeader(model);
        return "thymeleaf/board_search";
    }

    // 댓글 등록 요청 제출 (자유게시판 전용)
    @PostMapping("general/post/{postNum}/reply-submit")
    public String submitReply(
        @PathVariable("postNum") int postNum,
        @RequestParam(name = "replyContent", required = true) String replyContent
    ) {
        // 1) 로그인 상태가 아니거나
        // 2) INSERT 하기 위한 데이터가 전달되지 않은 경우
        if (
            !MemberUtility.isLoggedIn() ||
            replyContent == null || replyContent.isEmpty()
        ) return "redirect:/board/general/post/" + postNum;

        // 댓글 INSERT 처리
        replyDao.insert(new ReplyVo(replyContent, TimeUtility.getCurrentTimestamp(), JdbcOracleSpringApplication.currMemberNum, postNum));
        return "redirect:/board/general/post/" + postNum;
    }

    // 댓글 삭제 요청 제출 (자유게시판 전용)
    @PostMapping("general/post/{postNum}/reply-delete")
    public String deleteReply(
        @PathVariable("postNum") int postNum,
        @RequestParam(name = "replyNum", required = true) Integer replyNum
    ) {
        // 1) 로그인 상태가 아니거나
        // 2) 대상 댓글의 reply_num이 전달되지 않은 경우
        if (
            !MemberUtility.isLoggedIn() ||
            replyNum == null
        ) return "redirect:/board/general/post/" + postNum;

        replyEvaluationDao.deleteByReplyNum(replyNum);
        replyDao.deleteByReplyNumAndMemberNum(replyNum, JdbcOracleSpringApplication.currMemberNum);
        return "redirect:/board/general/post/" + postNum;
    }

    // 댓글 좋아요 요청 제출 (자유게시판 전용)
    @PostMapping("general/post/{postNum}/reply-like/{replyNum}")
    public String likeReply(
        @PathVariable("postNum") int postNum,
        @PathVariable(name = "replyNum", required = true) Integer replyNum
    ) {
        // 1) 로그인 상태가 아니거나
        // 2) 대상 댓글의 reply_num이 전달되지 않은 경우
        if (
            !MemberUtility.isLoggedIn() ||
            replyNum == null
        ) return "redirect:/board/general/post/" + postNum;

        // 댓글평가 기록이 없는 경우
        if (replyEvaluationDao.selectCountByMemberNumAndReplyNum(JdbcOracleSpringApplication.currMemberNum, replyNum) == 0) {
            replyEvaluationDao.insert(new ReplyEvaluationVo(JdbcOracleSpringApplication.currMemberNum, replyNum, ReplyEvaluationVo.LIKE));
            replyDao.increaseLikeCountByReplyNum(replyNum);
        }
        return "redirect:/board/general/post/" + postNum;
    }

    @PostMapping("general/post/{postNum}/reply-dislike/{replyNum}")
    public String dislikeReply(
        @PathVariable("postNum") int postNum,
        @PathVariable(name = "replyNum", required = true) Integer replyNum
    ) {
        // 1) 로그인 상태가 아니거나
        // 2) 대상 댓글의 reply_num이 전달되지 않은 경우
        if (
            !MemberUtility.isLoggedIn() ||
            replyNum == null
        ) return "redirect:/board/general/post/" + postNum;

        // 댓글평가 기록이 없는 경우
        if (replyEvaluationDao.selectCountByMemberNumAndReplyNum(JdbcOracleSpringApplication.currMemberNum, replyNum) == 0) {
            replyEvaluationDao.insert(new ReplyEvaluationVo(JdbcOracleSpringApplication.currMemberNum, replyNum, ReplyEvaluationVo.DISLIKE));
            replyDao.increaseDislikeCountByReplyNum(replyNum);
        }
        return "redirect:/board/general/post/" + postNum;
    }

    private void addAttributeToHeader(Model model) {
        model.addAttribute("logoText", "KH TOON 커뮤니티");
        model.addAttribute("toggleServiceName", "웹툰 조회 서비스로 이동");
        model.addAttribute("toggleServicePagePath", Path.WEBTOON_PAGE);
        model.addAttribute("serviceMainPagePath", Path.GENERAL_BOARD_PAGE);
        model.addAttribute("noticeBoardPagePath", Path.NOTICE_BOARD_PAGE);
        model.addAttribute("currMemberNickname", MemberUtility.isLoggedIn() ? memberDao.selectNameByMemberNum(JdbcOracleSpringApplication.currMemberNum) : null);
    }
}
