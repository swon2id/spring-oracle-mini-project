package com.kh.jdbc_oracle_spring.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GenreVo {
    private Integer genreNum;
    private String genreName;

    public final static String GENRE_NUM_STR = "GENRE_NUM";
    public final static String GENRE_NAME_STR = "GENRE_NAME";
}
