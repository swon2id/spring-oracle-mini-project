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
}
