package com.kh.jdbc_oracle_spring.controller;

import com.kh.jdbc_oracle_spring.JdbcOracleSpringApplication;
import com.kh.jdbc_oracle_spring.common.FavoriteGenreUtility;
import com.kh.jdbc_oracle_spring.common.MemberUtility;
import com.kh.jdbc_oracle_spring.dao.*;
import com.kh.jdbc_oracle_spring.vo.FavoriteGenreVo;
import com.kh.jdbc_oracle_spring.vo.MemberVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberDao memberDao;
    private final GenreDao genreDao;
    private final FavoriteGenreDao favoriteGenreDao;
    private final ReplyEvaluationDao replyEvaluationDao;
    private final ReplyDao replyDao;
    private final PostDao postDao;

    public MemberController(MemberDao memberDao, GenreDao genreDao, FavoriteGenreDao favoriteGenreDao, ReplyDao replyDao, ReplyEvaluationDao replyEvaluationDao, PostDao postDao) {
        this.memberDao = memberDao;
        this.genreDao = genreDao;
        this.favoriteGenreDao = favoriteGenreDao;
        this.replyDao = replyDao;
        this.replyEvaluationDao = replyEvaluationDao;
        this.postDao = postDao;
    }

    @GetMapping("register")
    public String register(
        @RequestParam(name = "referer", required = false) String referer,
        @RequestParam(name = "redirectFrom", required = false) String redirectFrom,
        Model model,
        HttpServletRequest request
    ) {
        if (redirectFrom == null) {
            referer = request.getHeader("Referer");
        } else {
            referer = redirectFrom;
        }
        model.addAttribute("referer", referer);
        model.addAttribute("prevPageUrl", referer);
        return "thymeleaf/register";
    }

    @GetMapping("login")
    public String enterLoginPage(
        @RequestParam(name = "referer", required = false) String referer,
        @RequestParam(name = "redirectFrom", required = false) String redirectFrom,
        Model model,
        HttpServletRequest request
    ) {
        if (redirectFrom == null) {
            referer = request.getHeader("Referer");
        } else {
            referer = redirectFrom;
        }
        model.addAttribute("referer", referer);
        model.addAttribute("prevPageUrl", referer);
        return "thymeleaf/login";
    }

    @GetMapping("mypage")
    public String enterMyPage(
        @RequestParam(name = "referer", required = false) String referer,
        @RequestParam(name = "redirectFrom", required = false) String redirectFrom,
        Model model,
        HttpServletRequest request
    ) {
        if (redirectFrom == null) {
            referer = request.getHeader("Referer");
        } else {
            referer = redirectFrom;
        }
        model.addAttribute("referer", referer);
        model.addAttribute("prevPageUrl", referer);

        if (!MemberUtility.isLoggedIn()) return "redirect:/";
        model.addAttribute("genres", genreDao.select());
        model.addAttribute("favoriteGenreNumList", favoriteGenreDao.selectGenreNumByMemberNum(JdbcOracleSpringApplication.currMemberNum));
        return "thymeleaf/mypage";
    }

    @PostMapping("register/submit")
    public String submitMemberRegistration(
        @RequestParam("id") String id,
        @RequestParam("pw") String pw,
        @RequestParam("email") String email,
        @RequestParam("nickname") String nickname,
        @RequestParam("birth") Date birthDate,
        @RequestParam(name = "referer", required = true) String referer
    ) {
        if (memberDao.insert(new MemberVo(id, pw, email, birthDate, nickname))) return "redirect:" + referer;
        return "redirect:/member/register?redirectFrom=" + referer;
    }

    @PostMapping("login/authentication")
    public String login(
        @RequestParam("id") String id,
        @RequestParam("pw") String pw,
        @RequestParam(name = "referer", required = true) String referer
    ) {
        if (MemberUtility.login(id, pw, memberDao.selectById(id))) return "redirect:" + referer;
        return "redirect:/member/login?redirectFrom=" + referer;
    }

    @PostMapping("logout")
    public String logout(
        HttpServletRequest request
    ) {
        MemberUtility.logout();
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("mypage/submit")
    public String submitFavoriteGenre(
        @RequestParam(name = "selectedGenres", required = false) List<String> selectedGenres,
        @RequestParam(name = "referer", required = true) String referer
    ) {
        if (!MemberUtility.isLoggedIn()) return "redirect:/";
        String pageUrlToRedirect = "redirect:/member/mypage?redirectFrom=" + referer;

        int memberNum = JdbcOracleSpringApplication.currMemberNum;
        if (selectedGenres == null || selectedGenres.isEmpty()) {
            favoriteGenreDao.deleteByMemberNum(memberNum);
            return pageUrlToRedirect;
        }
        else if (selectedGenres.size() > 3) return pageUrlToRedirect;

        List<FavoriteGenreVo> favoriteGenresWithGenreName = favoriteGenreDao.selectWithGenreNameByMemberNum(memberNum);
        if (favoriteGenresWithGenreName.size() > selectedGenres.size()) {
            int genreNumToDelete = FavoriteGenreUtility.getGenreNumToDelete(favoriteGenresWithGenreName, selectedGenres);
            if (genreNumToDelete == -1) return pageUrlToRedirect;
            favoriteGenreDao.deleteByMemberNumAndGenreNum(memberNum, genreNumToDelete);
        } else {
            int genreNumToInsert = FavoriteGenreUtility.getGenreNumToInsert(genreDao.select(), favoriteGenresWithGenreName, selectedGenres);
            if (genreNumToInsert == -1) return pageUrlToRedirect;
            favoriteGenreDao.insert(new FavoriteGenreVo(memberNum, genreNumToInsert));
        }
        return pageUrlToRedirect;
    }

    @PostMapping("mypage/delete")
    public String deleteMember() {
        if (!MemberUtility.isLoggedIn()) return "redirect:/";
        favoriteGenreDao.deleteByMemberNum(JdbcOracleSpringApplication.currMemberNum);

        List<Integer> posts = postDao.selectPostNumByMemberNum(JdbcOracleSpringApplication.currMemberNum);
        for (int postNum: posts) {
            // 현재 유저의 게시글에 달린 모든 댓글의 댓글 평가 삭제 후 해당 댓글 삭제
            List<Integer> replys = replyDao.selectReplyNumByPostNum(postNum);
            for (int replyNum: replys) {
                replyEvaluationDao.deleteByReplyNum(replyNum);
                replyDao.deleteByReplyNum(replyNum);
            }
        }
        // 현재 유저가 작성한 게시글 삭제
        postDao.deleteByMemberNum(JdbcOracleSpringApplication.currMemberNum);

        // 현재 유저가 작성한 댓글의 댓글 평가와 댓글 삭제
        List<Integer> replys = replyDao.selectReplyNumByMemberNum(JdbcOracleSpringApplication.currMemberNum);
        for (int replyNum: replys) {
            replyEvaluationDao.deleteByReplyNum(replyNum);
            replyDao.deleteByReplyNum(replyNum);
        }

        // 현재 유저가 남긴 모든 댓글 평가 삭제
        replyEvaluationDao.deleteByMemberNum(JdbcOracleSpringApplication.currMemberNum);

        // 현재 유저의 선호장르 기록 삭제
        favoriteGenreDao.deleteByMemberNum(JdbcOracleSpringApplication.currMemberNum);

        // 회원 삭제
        memberDao.delete(JdbcOracleSpringApplication.currMemberNum);
        MemberUtility.logout();
        return "redirect:/";
    }
}
