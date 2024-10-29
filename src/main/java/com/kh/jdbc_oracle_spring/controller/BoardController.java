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
    public String enterBoard(@PathVariable("boardType") String boardType, Model model) {
        if (!BoardUtility.isValidBoardType(boardType)) return "redirect:/board/general";

        List<PostVo> posts = postDao.selectWithMemberNameByBoardNum(BoardUtility.getBoardNum(boardType));
        model.addAttribute("boardName", BoardUtility.getBoardName(boardType));
        model.addAttribute("posts", posts);
        model.addAttribute("postUrls", PostUtility.getPostUrl(posts));
        model.addAttribute("isPostWritableMember",
            MemberUtility.isLoggedIn() &&
            PostUtility.isPostWritableMember(boardType, memberDao.selectMemberTypeNumByMemberNum(JdbcOracleSpringApplication.currMemberNum))
        );
        model.addAttribute("writePostUrl", boardType + "/write-post");
        addAttributeToHeader(model);
        return "thymeleaf/board";
    }

    @GetMapping("{boardType}/post/{postNum}")
    public String readPost(@PathVariable("boardType") String boardType, @PathVariable("postNum") int postNum, Model model) {
        if (!BoardUtility.isValidBoardType(boardType)) return "redirect:/board/general";

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

        if (boardType.equals("general")) {
            List<ReplyVo> replys = replyDao.selectWithMemberNicknameByPostNum(postNum);
            model.addAttribute("replys", replys);
            model.addAttribute("memberNum", JdbcOracleSpringApplication.currMemberNum);
            model.addAttribute("isLoggedIn", MemberUtility.isLoggedIn());
            model.addAttribute("replyNumListOfCurrMember", MemberUtility.isLoggedIn() ?
                    replyDao.selectReplyNumByMemberNum(JdbcOracleSpringApplication.currMemberNum)
                    : new ArrayList<>()
            );
        } else {
            model.addAttribute("replys", new ArrayList<>());
        }

        addAttributeToHeader(model);
        return "thymeleaf/post";
    }

    @GetMapping("{boardType}/write-post")
    public String writePost(@PathVariable("boardType") String boardType, Model model) {
        if (!BoardUtility.isValidBoardType(boardType) || !MemberUtility.isLoggedIn()) return "redirect:/board/general";
        model.addAttribute("boardName", BoardUtility.getBoardName(boardType));
        return "thymeleaf/write-post";
    }

    @PostMapping("{boardType}/write-post/submit")
    public String registPost(@PathVariable("boardType") String boardType, @RequestParam("postTitle") String postTitle, @RequestParam("postContent") String postContent) {
        int boardNum;
        if (
            !BoardUtility.isValidBoardType(boardType) ||
            !MemberUtility.isLoggedIn() ||
            !PostUtility.isPostWritableMember(boardType, memberDao.selectMemberTypeNumByMemberNum(JdbcOracleSpringApplication.currMemberNum)) ||
            postTitle == null || postTitle.isEmpty() ||
            postContent == null || postContent.isEmpty()
        ) return "redirect:/board/general";

        postDao.insert(new PostVo(postTitle, postContent, JdbcOracleSpringApplication.currMemberNum, BoardUtility.getBoardNum(boardType)));
        return "redirect:/board/" + boardType;
    }

    @GetMapping("search")
    public String searchPost(@RequestParam(name = "term", required = true) String term, Model model) {
        List<PostVo> posts = postDao.selectWithMemberNameByTerm(term);
        model.addAttribute("term", term);
        model.addAttribute("posts", posts);
        model.addAttribute("postUrls", PostUtility.getPostUrl(posts));
        addAttributeToHeader(model);
        return "thymeleaf/search/board_search";
    }

    @PostMapping("general/post/{postNum}/reply-submit")
    public String submitReply(@PathVariable("postNum") int postNum, @RequestParam(name = "replyContent", required = true) String replyContent, @RequestParam(name = "memberNum", required = false) Integer memberNum) {
        if (memberNum == null || replyContent.isEmpty()) return "redirect:/board/general/post/" + postNum;
        replyDao.insert(new ReplyVo(replyContent, TimeUtility.getCurrentTimestamp(), memberNum, postNum));
        return "redirect:/board/general/post/" + postNum;
    }

    @PostMapping("general/post/{postNum}/reply-delete")
    public String deleteReply(@PathVariable("postNum") int postNum, @RequestParam("replyNum") int replyNum) {
        replyEvaluationDao.deleteByReplyNum(replyNum);
        replyDao.deleteByReplyNum(replyNum);
        return "redirect:/board/general/post/" + postNum;
    }

    @PostMapping("general/post/{postNum}/reply-like/{replyNum}")
    public String likeReply(@PathVariable("postNum") int postNum, @PathVariable("replyNum") int replyNum) {
        if (replyEvaluationDao.selectCountByMemberNumAndReplyNum(JdbcOracleSpringApplication.currMemberNum, replyNum) == 0) {
            replyEvaluationDao.insert(new ReplyEvaluationVo(JdbcOracleSpringApplication.currMemberNum, replyNum, ReplyEvaluationVo.LIKE));
            replyDao.increaseLikeCountByReplyNum(replyNum);
        }
        return "redirect:/board/general/post/" + postNum;
    }

    @PostMapping("general/post/{postNum}/reply-dislike/{replyNum}")
    public String dislikeReply(@PathVariable("postNum") int postNum, @PathVariable("replyNum") int replyNum) {
        if (replyEvaluationDao.selectCountByMemberNumAndReplyNum(JdbcOracleSpringApplication.currMemberNum, replyNum) == 0) {
            replyEvaluationDao.insert(new ReplyEvaluationVo(JdbcOracleSpringApplication.currMemberNum, replyNum, ReplyEvaluationVo.DISLIKE));
            replyDao.increaseDislikeCountByReplyNum(replyNum);
        }
        return "redirect:/board/general/post/" + postNum;
    }

    private void addAttributeToHeader(Model model) {
        model.addAttribute("logoText", "KH TOON 커뮤니티");
        model.addAttribute("toggleServiceName", "웹툰");
        model.addAttribute("toggleServicePagePath", Path.WEBTOON_PAGE);
        model.addAttribute("serviceMainPagePath", Path.GENERAL_BOARD_PAGE);
        model.addAttribute("noticeBoardPagePath", Path.NOTICE_BOARD_PAGE);
    }
}
