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

    public final static int 드라마 = 1;
    public final static int 로맨스 = 2;
    public final static int 무협 = 3;
    public final static int 액션 = 4;
    public final static int 판타지 = 5;
    public final static int 기타 = 6;
}
