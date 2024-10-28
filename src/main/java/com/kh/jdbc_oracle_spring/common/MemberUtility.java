package com.kh.jdbc_oracle_spring.common;

import com.kh.jdbc_oracle_spring.JdbcOracleSpringApplication;
import com.kh.jdbc_oracle_spring.vo.MemberVo;

import java.util.List;

public class MemberUtility {
    public static boolean login(String id, String pw, List<MemberVo> members) {
        for (MemberVo member: members) {
            if(member.getMemberId().equals(id) && member.getMemberPw().equals(pw)) {
                JdbcOracleSpringApplication.currMemberNum = member.getMemberNum();
                return true;
            }
        }
        return false;
    }
}
