package com.kh.jdbc_oracle_spring.controller;

import com.kh.jdbc_oracle_spring.JdbcOracleSpringApplication;
import com.kh.jdbc_oracle_spring.common.Path;
import com.kh.jdbc_oracle_spring.common.TimeUtility;
import com.kh.jdbc_oracle_spring.common.WebtoonUtility;
import com.kh.jdbc_oracle_spring.dao.GenreDao;
import com.kh.jdbc_oracle_spring.dao.MemberDao;
import com.kh.jdbc_oracle_spring.dao.WebtoonDao;
import com.kh.jdbc_oracle_spring.vo.GenreVo;
import com.kh.jdbc_oracle_spring.vo.WebtoonVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final WebtoonDao webtoonDao;
    private final GenreDao genreDao;
    private final MemberDao memberDao;

    public MainController(WebtoonDao webtoonDao, GenreDao genreDao, MemberDao memberDao) {
        this.webtoonDao = webtoonDao;
        this.genreDao = genreDao;
        this.memberDao = memberDao;
    }

    @GetMapping("")
    public String initWebtoonPage(@RequestParam(name = "tab", required = false) String tab, @RequestParam(name = "genre", required = false) List<String> selectedGenres, Model model) {
        String dayOfWeek;
        if (tab == null || tab.isEmpty() || TimeUtility.convertDayOfWeekToInt(tab) == -1) dayOfWeek = TimeUtility.getDayOfWeek();
        else dayOfWeek = tab;

        List<GenreVo> genres = genreDao.select();
        List<WebtoonVo> naverWebtoons = webtoonDao.selectByGenreAndDayOfWeek(WebtoonVo.NAVER, selectedGenres, TimeUtility.convertDayOfWeekToInt(dayOfWeek));
        List<WebtoonVo> kakaoWebtoons = webtoonDao.selectByGenreAndDayOfWeek(WebtoonVo.KAKAO, selectedGenres, TimeUtility.convertDayOfWeekToInt(dayOfWeek));

        model.addAttribute("genres", genres);
        model.addAttribute("selectedGenres", selectedGenres != null ? selectedGenres : new ArrayList<>());
        model.addAttribute("currentTab", dayOfWeek);
        model.addAttribute("naverWebtoons", naverWebtoons);
        model.addAttribute("kakaoWebtoons", kakaoWebtoons);
        model.addAttribute("naverRecommendList", WebtoonUtility.getTop10List(webtoonDao.selectByPlatform(WebtoonVo.NAVER)));
        model.addAttribute("kakaoRecommendList", WebtoonUtility.getTop10List(webtoonDao.selectByPlatform(WebtoonVo.KAKAO)));
        addAttributeToHeader(model);
        return "thymeleaf/index";
    }

    // js 없애고, "" term 유효성 검사를 여기서 하자
    @GetMapping("search")
    public String searchWebtoon(@RequestParam(name = "term", required = true) String term, Model model) {
        model.addAttribute("term", term);
        addAttributeToHeader(model);
        return "thymeleaf/search/webtoon_search";
    }

    private void addAttributeToHeader(Model model) {
        model.addAttribute("logoText", "KH TOON");
        model.addAttribute("toggleServiceName", "게시판");
        model.addAttribute("toggleServicePagePath", Path.GENERAL_BOARD_PAGE);
        model.addAttribute("serviceMainPagePath", Path.WEBTOON_PAGE);
        model.addAttribute("currMemberNickname", JdbcOracleSpringApplication.currMemberNum != null ? memberDao.selectNameByMemberNum(JdbcOracleSpringApplication.currMemberNum) : null);
    }
}
