package com.kh.jdbc_oracle_spring.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FavoriteGenreVo {
    private Integer memberNum;
    private Integer genreNum;

    public FavoriteGenreVo(int memberNum, int genreNum) {
        this.memberNum = memberNum;
        this.genreNum = genreNum;
    }

    // GENRE 테이블 조인 맵핑 용도
    public FavoriteGenreVo(int memberNum, int genreNum, String genreName) {
        this(memberNum, genreNum);
        this.genreName = genreName;
    }

    // GENRE 테이블 조인 컬럼
    private String genreName = null;
}
