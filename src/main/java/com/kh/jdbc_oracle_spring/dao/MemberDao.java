package com.kh.jdbc_oracle_spring.dao;

import com.kh.jdbc_oracle_spring.JdbcOracleSpringApplication;
import com.kh.jdbc_oracle_spring.common.MemberUtility;
import com.kh.jdbc_oracle_spring.common.TimeUtility;
import com.kh.jdbc_oracle_spring.vo.MemberVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    public List<MemberVo> selectById(String memberId) {
        String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = '" + memberId + "'";
        return jdbcTemplate.query(sql, (rs, rownum) -> new MemberVo(
                rs.getInt("MEMBER_NUM"),
                rs.getString("MEMBER_ID"),
                rs.getString("MEMBER_PW")
            )
        );
    }

    public String selectNameByMemberNum(Integer memberNum) {
        String sql = "SELECT MEMBER_NICKNAME FROM MEMBER WHERE MEMBER_NUM = " + memberNum;
        List<String> memberNicknames = jdbcTemplate.queryForList(sql, String.class);
        return memberNicknames.get(0);
    }

    public boolean insert(MemberVo vo) {
        String sql = "INSERT INTO MEMBER VALUES(SEQ_MEMBER_SEQUENCE.nextval, ?, ?, ?, ?, ?, ?, ? ,?)";
        return 1 == jdbcTemplate.update(sql, vo.getMemberId(), vo.getMemberPw(), vo.getMemberEmail(), vo.getMemberBirth(), vo.getMemberNickname(), vo.getMemberRegistrationDate(), vo.getMemberExist(), vo.getMemberTypeNum());
    }
}
