package com.kh.jdbc_oracle_spring.dao;

import com.kh.jdbc_oracle_spring.vo.GenreVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class GenreDao {
    private final JdbcTemplate jdbcTemplate;

    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<GenreVo> select() {
        String sql = "SELECT * FROM GENRE";
        return jdbcTemplate.query(sql, (rs, rownum) -> new GenreVo(
            rs.getInt("GENRE_NUM"),
            rs.getString("GENRE_NAME")
        ));
    }
}
