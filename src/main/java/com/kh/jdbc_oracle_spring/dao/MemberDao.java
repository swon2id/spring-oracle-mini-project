package com.kh.jdbc_oracle_spring.dao;

import com.kh.jdbc_oracle_spring.vo.MemberVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;
    private static MemberVo vo = new MemberVo(1, "admin", "1234", "admin@admin", Date.valueOf("2024-10-27"), "난운영자", Date.valueOf("1999-10-27"), 1, 0);

    public MemberDao(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    public MemberVo selectById(String memberId) {
        return vo;
    }

    public MemberVo selectByMemberNum (Integer memberNum) {
        return vo;
    }
}
