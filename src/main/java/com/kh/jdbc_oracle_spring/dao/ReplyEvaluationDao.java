package com.kh.jdbc_oracle_spring.dao;

import com.kh.jdbc_oracle_spring.vo.ReplyEvaluationVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReplyEvaluationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReplyEvaluationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int selectCountByMemberNumAndReplyNum(int memberNum, int replyNum) {
        String sql = "SELECT COUNT(*) FROM REPLY_EVALUATION WHERE MEMBER_NUM = ? AND REPLY_NUM = ?";
        List<Integer> memberEvaluationCountList = jdbcTemplate.queryForList(sql, new Object[]{memberNum, replyNum}, Integer.class);
        return memberEvaluationCountList.isEmpty() ? 0 : memberEvaluationCountList.get(0);
    }

    public void insert(ReplyEvaluationVo vo) {
        String sql = "INSERT INTO REPLY_EVALUATION VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, vo.getMemberNum(), vo.getReplyNum(), vo.getReplyEvaluationType());
    }

    public void deleteByReplyNum(int replyNum) {
        String sql = "DELETE FROM REPLY_EVALUATION WHERE REPLY_NUM = ?";
        jdbcTemplate.update(sql, replyNum);
    }
}
