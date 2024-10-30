package com.kh.jdbc_oracle_spring.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WebtoonVo {
    private Integer webtoonNum;
    private String webtoonTitle;
    private String webtoonAuthor;
    private Integer genreNum;
    private Integer webtoonAvailableAge;
    private double webtoonRating;
    private Integer webtoonSubscribeCount;
    private Integer webtoonViewCount;
    private Integer webtoonReleaseDay;
    private String webtoonPageUrl;
    private String webtoonThumbnailUrl;
    private Integer platformNum;

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

    public WebtoonVo(int webtoonNum, String webtoonTitle, String webtoonAuthor, int genreNum,
                     int webtoonAvailableAge, double webtoonRating, Integer webtoonSubscribeCount,
                     Integer webtoonViewCount, int webtoonReleaseDay, String webtoonPageUrl,
                     String webtoonThumbnailUrl, int platformNum, String genreName) {
        this(webtoonNum, webtoonTitle, webtoonAuthor, genreNum,
                webtoonAvailableAge, webtoonRating, webtoonSubscribeCount,
                webtoonViewCount, webtoonReleaseDay, webtoonPageUrl,
                webtoonThumbnailUrl, platformNum);
        this.genreName = genreName;
    }

    // GENRE 테이블 조인 컬럼
    private String genreName = null;

    // platformNum 도메인 구분
    public final static int NAVER = 1;
    public final static int KAKAO = 2;
}
