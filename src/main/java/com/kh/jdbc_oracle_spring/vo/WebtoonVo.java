package com.kh.jdbc_oracle_spring.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WebtoonVo {
    private int webtoonNum;
    private String webtoonTitle;
    private String webtoonAuthor;
    private int genreNum;
    private int webtoonAvailableAge;
    private double webtoonRating;
    private int webtoonSubscribeCount;
    private Integer webtoonViewCount;
    private Integer webtoonReleaseDay;
    private String webtoonPageUrl;
    private String webtoonThumbnailUrl;
    private int platformNum;

    // DB에서 각 컬럼 이름에 대응하는 문자열
    public final static String WEBTOON_NUM_STR = "WEBTOON_NUM";
    public final static String WEBTOON_TITLE_STR = "WEBTOON_TITLE";
    public final static String WEBTOON_AUTHOR_STR = "WEBTOON_AUTHOR";
    public final static String GENRE_NUM_STR = "GENRE_NUM";
    public final static String WEBTOON_AVAILABLE_AGE_STR = "WEBTOON_AVAILABLE_AGE";
    public final static String WEBTOON_RATING_STR = "WEBTOON_RATING";
    public final static String WEBTOON_SUBSCRIBE_COUNT_STR = "WEBTOON_SUBSCRIBE_COUNT";
    public final static String WEBTOON_VIEW_COUNT_STR = "WEBTOON_VIEW_COUNT";
    public final static String WEBTOON_RELEASE_DAY_STR = "WEBTOON_RELEASE_DAY";
    public final static String WEBTOON_PAGE_URL_STR = "WEBTOON_PAGE_URL";
    public final static String WEBTOON_THUMBNAIL_URL_STR = "WEBTOON_THUMBNAIL_URL";
    public final static String PLATFORM_NUM_STR = "PLATFORM_NUM";

    // platformNum 도메인 구분
    public final static int NAVER = 0;
    public final static int KAKAO = 1;

    public WebtoonVo(int webtoonNum, String webtoonTitle, String webtoonAuthor, int genreNum,
                     int webtoonAvailableAge, double webtoonRating, Integer webtoonSubscribeCount,
                     Integer webtoonViewCount, int webtoonReleaseDay, String webtoonPageUrl,
                     String webtoonThumbnailUrl, int platformNum) {
        this.webtoonNum = webtoonNum;
        this.webtoonTitle = webtoonTitle;
        this.webtoonAuthor = webtoonAuthor;
        this.genreNum = genreNum;
        this.webtoonAvailableAge = webtoonAvailableAge;
        this.webtoonRating = webtoonRating;
        if (webtoonSubscribeCount != null) this.webtoonSubscribeCount = webtoonSubscribeCount;
        if (webtoonViewCount != null) this.webtoonViewCount = webtoonViewCount;
        this.webtoonReleaseDay = webtoonReleaseDay;
        this.webtoonPageUrl = webtoonPageUrl;
        this.webtoonThumbnailUrl = webtoonThumbnailUrl;
        this.platformNum = platformNum;
    }

    // join 되는 속성
    private String genreName = null;
}
