package com.kh.jdbc_oracle_spring.controller;

import com.kh.jdbc_oracle_spring.JdbcOracleSpringApplication;
import com.kh.jdbc_oracle_spring.common.FavoriteGenreUtility;
import com.kh.jdbc_oracle_spring.common.MemberUtility;
import com.kh.jdbc_oracle_spring.dao.FavoriteGenreDao;
import com.kh.jdbc_oracle_spring.dao.GenreDao;
import com.kh.jdbc_oracle_spring.dao.MemberDao;
import com.kh.jdbc_oracle_spring.vo.FavoriteGenreVo;
import com.kh.jdbc_oracle_spring.vo.MemberVo;
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

    public MemberController(MemberDao memberDao, GenreDao genreDao, FavoriteGenreDao favoriteGenreDao) {
        this.memberDao = memberDao;
        this.genreDao = genreDao;
        this.favoriteGenreDao = favoriteGenreDao;
    }

    @GetMapping("login")
    public String enterLoginPage() {
        return "thymeleaf/login";
    }

    @PostMapping("login/authentication")
    public String login(@RequestParam("id") String id, @RequestParam("pw") String pw) {
        // 로그인 성공
        if (MemberUtility.login(id, pw, memberDao.selectById(id))) return "redirect:/";

        // 로그인 실패
        return "thymeleaf/login";
    }

    @GetMapping("logout")
    public String logout() {
        JdbcOracleSpringApplication.currMemberNum = null;
        return "redirect:/";
    }

    @GetMapping("register")
    public String register() {
        return "thymeleaf/register";
    }

    @PostMapping("register/submit")
    public String submitMemberRegistration(@RequestParam("id") String id, @RequestParam("pw") String pw, @RequestParam("email") String email, @RequestParam("nickname") String nickname, @RequestParam("birth") Date birthDate) {
        // 회원 가입 실패
        if (memberDao.insert(new MemberVo(id, pw, email, birthDate, nickname))) return "redirect:/";

        // 회원 가입 성공
        return "thymeleaf/register";
    }

    @GetMapping("mypage")
    public String enterMyPage(Model model) {
        model.addAttribute("genres", genreDao.select());
        model.addAttribute("favoriteGenreNumList", favoriteGenreDao.selectGenreNumByMemberNum(JdbcOracleSpringApplication.currMemberNum));
        return "thymeleaf/mypage";
    }

    @PostMapping("mypage/submit")
    public String submitFavoriteGenre(@RequestParam(name = "selectedGenres", required = false) List<String> selectedGenres) {
        String pageUrlToRedirect = "redirect:/member/mypage";
        int memberNum = JdbcOracleSpringApplication.currMemberNum;
        if (selectedGenres == null || selectedGenres.isEmpty()) {
            favoriteGenreDao.deleteAllByMemberNum(memberNum);
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
}
