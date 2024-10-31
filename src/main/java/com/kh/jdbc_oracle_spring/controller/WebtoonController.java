package com.kh.jdbc_oracle_spring.controller;

import com.kh.jdbc_oracle_spring.JdbcOracleSpringApplication;
import com.kh.jdbc_oracle_spring.common.MemberUtility;
import com.kh.jdbc_oracle_spring.common.Path;
import com.kh.jdbc_oracle_spring.common.TimeUtility;
import com.kh.jdbc_oracle_spring.common.WebtoonUtility;
import com.kh.jdbc_oracle_spring.dao.FavoriteGenreDao;
import com.kh.jdbc_oracle_spring.dao.GenreDao;
import com.kh.jdbc_oracle_spring.dao.MemberDao;
import com.kh.jdbc_oracle_spring.dao.WebtoonDao;
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
public class WebtoonController {
    private final WebtoonDao webtoonDao;
    private final GenreDao genreDao;
    private final MemberDao memberDao;
    private final FavoriteGenreDao favoriteGenreDao;

    public WebtoonController(WebtoonDao webtoonDao, GenreDao genreDao, MemberDao memberDao, FavoriteGenreDao favoriteGenreDao) {
        this.webtoonDao = webtoonDao;
        this.genreDao = genreDao;
        this.memberDao = memberDao;
        this.favoriteGenreDao = favoriteGenreDao;
    }

    @GetMapping("")
    public String initWebtoonMainPage(
            @RequestParam(name = "tab", required = false) String tab,
            @RequestParam(name = "genre", required = false) List<String> selectedGenres,
            Model model
    ) {
        String dayOfWeek;
        if (
            tab == null || tab.isEmpty() ||
            !WebtoonUtility.isValidDayParam(tab)
        ) dayOfWeek = TimeUtility.getDayOfWeek();
        else dayOfWeek = tab;

        List<WebtoonVo> naverWebtoons;
        List<WebtoonVo> kakaoWebtoons;
        if (selectedGenres == null || selectedGenres.isEmpty()) {
            naverWebtoons = webtoonDao.selectByReleaseDay(
                WebtoonVo.NAVER,
                WebtoonUtility.convertDayOfWeekTabToInt(dayOfWeek)
            );
            kakaoWebtoons = webtoonDao.selectByReleaseDay(
                WebtoonVo.KAKAO,
                WebtoonUtility.convertDayOfWeekTabToInt(dayOfWeek)
            );
        } else {
            naverWebtoons = webtoonDao.selectByGenreAndReleaseDay (
                WebtoonVo.NAVER,
                WebtoonUtility.convertSelectedGenresToIntList(selectedGenres),
                WebtoonUtility.convertDayOfWeekTabToInt(dayOfWeek)
            );
            kakaoWebtoons = webtoonDao.selectByGenreAndReleaseDay(
                WebtoonVo.KAKAO,
                WebtoonUtility.convertSelectedGenresToIntList(selectedGenres),
                WebtoonUtility.convertDayOfWeekTabToInt(dayOfWeek)
            );
        }

        model.addAttribute("genres", genreDao.select());
        model.addAttribute("selectedGenres", selectedGenres != null ? selectedGenres : new ArrayList<>());
        model.addAttribute("currentTab", dayOfWeek);
        model.addAttribute("naverWebtoons", naverWebtoons);
        model.addAttribute("kakaoWebtoons", kakaoWebtoons);

        List<Integer> memberFavoriteGenres = MemberUtility.isLoggedIn() ?  favoriteGenreDao.selectGenreNumByMemberNum(JdbcOracleSpringApplication.currMemberNum) : new ArrayList<>();
        model.addAttribute("naverRecommendList", WebtoonUtility.getTop10List(webtoonDao.selectByPlatform(WebtoonVo.NAVER), memberFavoriteGenres));
        model.addAttribute("kakaoRecommendList", WebtoonUtility.getTop10List(webtoonDao.selectByPlatform(WebtoonVo.KAKAO), memberFavoriteGenres));
        addAttributeToHeader(model);
        return "thymeleaf/index";
    }

    // js 없애고, "" term 유효성 검사를 여기서 하자
    @GetMapping("search")
    public String searchWebtoon(
            @RequestParam(name = "term", required = true) String term,
            Model model
    ) {
        List<WebtoonVo> webtoons = webtoonDao.selectWithGenreNameByTerm(term);
        model.addAttribute("term", term);
        model.addAttribute("webtoons", webtoons);
        addAttributeToHeader(model);
        return "thymeleaf/webtoon_search";
    }

    private void addAttributeToHeader(Model model) {
        model.addAttribute("logoText", "KH TOON");
        model.addAttribute("toggleServiceName", "커뮤니티 서비스로 이동");
        model.addAttribute("toggleServicePagePath", Path.GENERAL_BOARD_PAGE);
        model.addAttribute("serviceMainPagePath", Path.WEBTOON_PAGE);
        model.addAttribute("currMemberNickname", MemberUtility.isLoggedIn() ? memberDao.selectNameByMemberNum(JdbcOracleSpringApplication.currMemberNum) : null);
    }
}
