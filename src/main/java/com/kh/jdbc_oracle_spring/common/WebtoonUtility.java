package com.kh.jdbc_oracle_spring.common;

import com.kh.jdbc_oracle_spring.vo.GenreVo;
import com.kh.jdbc_oracle_spring.vo.PostVo;
import com.kh.jdbc_oracle_spring.vo.WebtoonVo;

import java.util.*;
import java.util.stream.Collectors;

public class WebtoonUtility {
    private static final int 드라마 = GenreVo.드라마;
    private static final int 로맨스 = GenreVo.로맨스;
    private static final int 무협 = GenreVo.무협;
    private static final int 액션 = GenreVo.액션;
    private static final int 판타지 = GenreVo.판타지;
    private static final int 기타 = GenreVo.기타;

    /**
     * 평점을 기준으로 상위 10개의 웹툰VO 리스트를 가져옵니다.
     *
     * @param webtoons      웹툰VO 리스트
     * @return 평점을 기준으로 내림차순으로 정렬된 상위 10개의 웹툰VO 리스트
     */
    public static List<WebtoonVo> getTop10List(List<WebtoonVo> webtoons) {
        return webtoons.stream()
                .sorted(Comparator.comparing(WebtoonVo::getWebtoonRating).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public static List<Integer> convertSelectedGenresToIntList(List<String> selectedGenres) {
        List<Integer> selectedGenresInt = new ArrayList<>();
        for (String selectedGenre: selectedGenres) {
            switch (selectedGenre) {
                case "드라마": selectedGenresInt.add(드라마); break;
                case "로맨스": selectedGenresInt.add(로맨스); break;
                case "무협": selectedGenresInt.add(무협); break;
                case "액션": selectedGenresInt.add(액션); break;
                case "판타지": selectedGenresInt.add(판타지); break;
                case "기타": selectedGenresInt.add(기타); break;
                default: return new ArrayList<>();
            }
        }
        return selectedGenresInt;
    }

    public static int convertDayOfWeekTabToInt(String dayOfWeek) {
        return switch (dayOfWeek) {
            case "mon" -> 1;
            case "tue" -> 2;
            case "wed" -> 3;
            case "thu" -> 4;
            case "fri" -> 5;
            case "sat" -> 6;
            case "sun" -> 7;
            default -> -1;
        };
    }

    public static boolean isValidDayParam(String tab) {
        return convertDayOfWeekTabToInt(tab) != -1;
    }
}
