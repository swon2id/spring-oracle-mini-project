package com.kh.jdbc_oracle_spring.dao;

import com.kh.jdbc_oracle_spring.vo.PostVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDao {
    private final JdbcTemplate jdbcTemplate;

    public PostDao(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    public List<PostVo> selectByBoardNum(int boardNum) {
        String sql = "SELECT * FROM POST WHERE BOARD_NUM = " + boardNum;
        return jdbcTemplate.query(sql, (rs, rownum) -> new PostVo(
            rs.getInt("POST_NUM"),
            rs.getString("POST_TITLE"),
            rs.getString("POST_CONTENT"),
            rs.getDate("POST_PUBLISHED_DATE"),
            rs.getInt("MEMBER_NUM"),
            rs.getInt("BOARD_NUM")
        ));
    }
}