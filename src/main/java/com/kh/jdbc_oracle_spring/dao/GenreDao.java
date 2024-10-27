package com.kh.jdbc_oracle_spring.dao;

import com.kh.jdbc_oracle_spring.vo.GenreVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GenreDao {
    private final JdbcTemplate jdbcTemplate;

    private final static String GENRE_NUM_STR = "GENRE_NUM";
    private final static String GENRE_NAME_STR = "GENRE_NAME";

    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<GenreVo> select() {
        List<GenreVo> genres = new ArrayList<>();
        genres.add(new GenreVo(1, "장르1"));
        genres.add(new GenreVo(2, "장르2"));
        genres.add(new GenreVo(3, "장르3"));
        genres.add(new GenreVo(4, "장르4"));
        genres.add(new GenreVo(5, "장르5"));
        genres.add(new GenreVo(6, "장르6"));
        genres.add(new GenreVo(7, "장르7"));
        genres.add(new GenreVo(8, "장르8"));
        return genres;
    }
}
