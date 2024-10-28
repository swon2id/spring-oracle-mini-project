package com.kh.jdbc_oracle_spring.common;

import com.kh.jdbc_oracle_spring.vo.WebtoonVo;

import java.util.*;
import java.util.stream.Collectors;

public class WebtoonUtility {
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

    public static List<WebtoonVo> getDummyList(int platform, List<String> selectedGenres, int dayOfWeek) {
        List<WebtoonVo> list = new ArrayList<>();
        int[] genres = {1, 2, 3, 4, 5, 6, 7, 8}; // 장르 번호

        String pageUrl = "comic.naver.com/webtoon/list?titleId=777767&tab=fri";
        String thumbnailUrl = "image-comic.pstatic.net/webtoon/777767/thumbnail/thumbnail_IMAG21_cc85f891-272b-450a-b642-cffe1568ab71.jpg";

        // 더미 웹툰 데이터 생성
        for (int i = 1; i <= 100; i++) {
            String title = "웹툰제목" + i;
            switch (dayOfWeek) {
                case 0: title = "일요일 " + title; break;
                case 1: title = "월요일 " + title; break;
                case 2: title = "화요일 " + title; break;
                case 3: title = "수요일 " + title; break;
                case 4: title = "목요일 " + title; break;
                case 5: title = "금요일 " + title; break;
                case 6: title = "토요일 " + title; break;
            }
            String author = "웹툰작가" + i;
            double rating = 4.0 + (i % 6); // 평점: 4.0 ~ 9.0
            Integer subscribeCount = (platform == 0) ? i * 5000 : null;
            Integer viewCount = (platform == 1) ? i * 5000 : null;
            int availableAge = (i % 3 == 0) ? 15 : 12;

            WebtoonVo vo = new WebtoonVo(
                    i,
                    title,
                    author,
                    genres[i % genres.length], // 장르 번호 할당
                    availableAge,
                    rating,
                    subscribeCount,
                    viewCount,
                    dayOfWeek,
                    pageUrl,
                    thumbnailUrl,
                    platform
            );
            vo.setGenreName("장르" + genres[i % genres.length]); // 장르 이름 설정
            list.add(vo);
        }

        // 플랫폼과 요일로 필터링
        List<WebtoonVo> filteredList = list.stream()
                .filter(webtoon -> webtoon.getPlatformNum() == platform && webtoon.getWebtoonReleaseDay() == dayOfWeek)
                .collect(Collectors.toList());

        // 선택된 장르가 있을 경우, 장르로 추가 필터링
        if (selectedGenres != null && !selectedGenres.isEmpty()) {
            // "장르1", "장르2" 형식의 genreName에서 숫자 추출하여 genreNum으로 변환
            Set<Integer> selectedGenreNums = selectedGenres.stream()
                    .map(genreName -> {
                        if (genreName.startsWith("장르")) {
                            try {
                                return Integer.parseInt(genreName.substring(2));
                            } catch (NumberFormatException e) {
                                // 잘못된 형식의 genreName 처리 (필터링에서 제외)
                                return null;
                            }
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            // 장르 번호로 필터링
            filteredList = filteredList.stream()
                    .filter(webtoon -> selectedGenreNums.contains(webtoon.getGenreNum()))
                    .collect(Collectors.toList());
        }

        return filteredList;
    }
}
