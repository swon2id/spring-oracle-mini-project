package com.kh.jdbc_oracle_spring.dao;

import com.kh.jdbc_oracle_spring.vo.MemberVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
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

    public int selectMemberTypeNumByMemberNum(int memberNum) {
        String sql = "SELECT MEMBER_TYPE_NUM FROM MEMBER WHERE MEMBER_NUM = ?";
        List<Integer> memberTypeList = jdbcTemplate.queryForList(sql, new Object[]{memberNum}, Integer.class);
        return memberTypeList.isEmpty() ? -1 : memberTypeList.get(0);
    }

    public String selectNameByMemberNum(Integer memberNum) {
        String sql = "SELECT MEMBER_NICKNAME FROM MEMBER WHERE MEMBER_NUM = " + memberNum;
        List<String> memberNicknames = jdbcTemplate.queryForList(sql, String.class);
        return memberNicknames.get(0);
    }

    public boolean insert(MemberVo vo) {
        String sql = "INSERT INTO MEMBER VALUES(SEQ_MEMBER_SEQUENCE.nextval, ?, ?, ?, ?, ?, ?, ? ,?)";
        int res = 0;
        try {
            res = jdbcTemplate.update(sql, vo.getMemberId(), vo.getMemberPw(), vo.getMemberEmail(), vo.getMemberBirth(), vo.getMemberNickname(), vo.getMemberRegistrationDate(), vo.getMemberExist(), vo.getMemberTypeNum());
        } catch(Exception e) {
            System.out.println("멤버 등록 실패 : " + e.getMessage());
        }
        return res == 1;
    }

    public void delete(int memberNum) {
        String sql = "DELETE FROM MEMBER WHERE MEMBER_NUM = ?";
        jdbcTemplate.update(sql, memberNum);
    }
}
