package com.kh.jdbc_oracle_spring.controller;

import com.kh.jdbc_oracle_spring.common.Paths;
import com.kh.jdbc_oracle_spring.common.TimeUtility;
import com.kh.jdbc_oracle_spring.dao.WebtoonDao;
import com.kh.jdbc_oracle_spring.vo.WebtoonVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class WebtoonController {
    private final WebtoonDao webtoonDao;
    public final static int NAVER = 0;
    public final static int KAKAO = 1;

    public WebtoonController(WebtoonDao webtoonDao) {
        this.webtoonDao = webtoonDao;
    }

    @GetMapping("")
    public String enterMainPage(@RequestParam(name = "tab", required = false) String tab, Model model) {
        int dayOfWeek;
        if (tab == null || tab.isEmpty()) dayOfWeek = TimeUtility.getDayOfWeek();
        else if ((dayOfWeek = TimeUtility.convertDayOfWeekToInt(tab)) == -1) dayOfWeek = TimeUtility.getDayOfWeek();

        List<WebtoonVo> webtoons = webtoonDao.selectWebtoonByPlatform(NAVER, dayOfWeek);
        for (WebtoonVo vo: webtoons) {
            System.out.println(vo.getWebtoonNum());
            System.out.println(vo.getWebtoonTitle());
        }

        addHeaderAttribute(model);
        return "thymeleaf/index";
    }
    
    @GetMapping("search")
    public String searchWebtoon(@RequestParam(name = "term", required = true) String term, Model model) {
        System.out.println("검색어 : " + term);
        model.addAttribute("term", term);
        addHeaderAttribute(model);
        return "thymeleaf/search/webtoon_search";
    }

    private void addHeaderAttribute(Model model) {
        model.addAttribute("toggleServiceName", "게시판");
        model.addAttribute("toggleServicePagePath", Paths.GENERAL_BOARD_PAGE);
        model.addAttribute("serviceMainPagePath", Paths.WEBTOON_PAGE);
    }
}
