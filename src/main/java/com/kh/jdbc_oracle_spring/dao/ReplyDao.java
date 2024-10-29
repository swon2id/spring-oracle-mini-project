package com.kh.jdbc_oracle_spring.dao;

import com.kh.jdbc_oracle_spring.vo.ReplyVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReplyDao {
    private final JdbcTemplate jdbcTemplate;

    public ReplyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReplyVo> selectWithMemberNicknameByPostNum(int postNum) {
        String sql = "SELECT r.REPLY_NUM, r.REPLY_CONTENT, r.REPLY_PUBLISHED_DATE, r.REPLY_LIKE_COUNT, r.REPLY_DISLIKE_COUNT, r.POST_NUM, m.MEMBER_NICKNAME"
                + " FROM REPLY r"
                + " JOIN MEMBER m"
                + " USING(MEMBER_NUM)"
                + " WHERE r.POST_NUM = " + postNum;
        return jdbcTemplate.query(sql, (rs, rownum) -> new ReplyVo(
            rs.getInt("REPLY_NUM"),
            rs.getString("REPLY_CONTENT"),
            rs.getTimestamp("REPLY_PUBLISHED_DATE"),
            rs.getInt("REPLY_LIKE_COUNT"),
            rs.getInt("REPLY_DISLIKE_COUNT"),
            rs.getInt("POST_NUM"),
            rs.getString("MEMBER_NICKNAME")
        ));
    }

    public List<Integer> selectReplyNumByMemberNum(int memberNum) {
        String sql = "SELECT REPLY_NUM FROM REPLY WHERE MEMBER_NUM = " + memberNum;
        return jdbcTemplate.queryForList(sql, Integer.class);
    }

    public void insert(ReplyVo vo) {
        String sql = "INSERT INTO REPLY VALUES(SEQ_REPLY_SEQUENCE.nextval, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, vo.getReplyContent(), vo.getReplyPublishedDate(), vo.getReplyLikeCount(), vo.getReplyDislikeCount(), vo.getMemberNum(), vo.getPostNum());
    }

    public void deleteByReplyNum(int replyNum) {
        String sql = "DELETE FROM REPLY WHERE REPLY_NUM = " + replyNum;
        jdbcTemplate.update(sql);
    }

    public void increaseLikeCountByReplyNum(int replyNum) {
        String sql = "UPDATE REPLY SET REPLY_LIKE_COUNT = REPLY_LIKE_COUNT+1 WHERE REPLY_NUM = ?";
        jdbcTemplate.update(sql, replyNum);
    }

    public void increaseDislikeCountByReplyNum(int replyNum) {
        String sql = "UPDATE REPLY SET REPLY_DISLIKE_COUNT = REPLY_LIKE_COUNT+1 WHERE REPLY_NUM = ?";
        jdbcTemplate.update(sql, replyNum);
    }
}