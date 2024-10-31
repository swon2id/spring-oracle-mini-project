package com.kh.jdbc_oracle_spring.dao;

import com.kh.jdbc_oracle_spring.vo.PostVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostDao {
    private final JdbcTemplate jdbcTemplate;

    public PostDao(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    public List<PostVo> selectWithMemberNameByBoardNum(int boardNum) {
        String sql = "SELECT p.POST_NUM, p.POST_TITLE, p.POST_CONTENT, p.POST_PUBLISHED_DATE, p.POST_VISIT, p.BOARD_NUM, m.MEMBER_NICKNAME"
        + " FROM POST p"
        + " JOIN MEMBER m"
        + " USING(MEMBER_NUM)"
        + " WHERE p.BOARD_NUM = " + boardNum
        + " ORDER BY p.POST_PUBLISHED_DATE DESC, p.POST_NUM";

        return jdbcTemplate.query(sql, (rs, rownum) -> new PostVo(
            rs.getInt("POST_NUM"),
            rs.getString("POST_TITLE"),
            rs.getString("POST_CONTENT"),
            rs.getTimestamp("POST_PUBLISHED_DATE"),
            rs.getInt("POST_VISIT"),
            rs.getInt("BOARD_NUM"),
            rs.getString("MEMBER_NICKNAME")
        ));
    }

    public PostVo selectPostWithMemberNameByPostNum(int postNum) {
        String sql = "SELECT p.POST_NUM, p.POST_TITLE, p.POST_CONTENT, p.POST_PUBLISHED_DATE, p.POST_VISIT, p.BOARD_NUM, m.MEMBER_NICKNAME"
                + " FROM POST p"
                + " JOIN MEMBER m"
                + " USING(MEMBER_NUM)"
                + " WHERE p.POST_NUM = " + postNum;
        return jdbcTemplate.query(sql, new PostRowMapper()).get(0);
    }

    public List<PostVo> selectWithMemberNameByTerm(String term) {
        String sql = "SELECT p.POST_NUM, p.POST_TITLE, p.POST_CONTENT, p.POST_PUBLISHED_DATE, p.POST_VISIT, p.BOARD_NUM, m.MEMBER_NICKNAME"
                + " FROM POST p JOIN MEMBER m"
                + " USING(MEMBER_NUM)"
                + " WHERE p.POST_TITLE LIKE '%" + term + "%'"
                + " OR p.POST_CONTENT LIKE '%" + term + "%'"
                + " ORDER BY p.POST_PUBLISHED_DATE DESC, p.POST_NUM";

        return jdbcTemplate.query(sql, new PostRowMapper());
    }

    public void insert(PostVo vo) {
        String sql = "INSERT INTO POST VALUES(SEQ_POST_SEQUENCE.nextval, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, vo.getPostTitle(), vo.getPostContent(), vo.getPostPublishedDate(), vo.getPostVisit(), vo.getMemberNum(), vo.getBoardNum());
    }

    public void increaseVisitCountByPostNum(int postNum) {
        String sql = "UPDATE POST SET POST_VISIT = POST_VISIT+1 WHERE POST_NUM = ?";
        jdbcTemplate.update(sql, postNum);
    }

    private static class PostRowMapper implements RowMapper<PostVo> {
        @Override
        public PostVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PostVo(
                rs.getInt("POST_NUM"),
                rs.getString("POST_TITLE"),
                rs.getString("POST_CONTENT"),
                rs.getTimestamp("POST_PUBLISHED_DATE"),
                rs.getInt("POST_VISIT"),
                rs.getInt("BOARD_NUM"),
                rs.getString("MEMBER_NICKNAME")
            );
        }
    };
}