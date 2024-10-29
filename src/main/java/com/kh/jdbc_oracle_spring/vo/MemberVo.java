package com.kh.jdbc_oracle_spring.vo;

import com.kh.jdbc_oracle_spring.common.TimeUtility;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberVo {
    private Integer memberNum;
    private String memberId;
    private String memberPw;
    private String memberEmail;
    private Date memberBirth;
    private String memberNickname;
    private Timestamp memberRegistrationDate;
    private Integer memberExist;
    private Integer memberTypeNum;

    public MemberVo(String memberNickname) { this.memberNickname = memberNickname; }

    // 로그인 SELECT 맵핑 용도
    public MemberVo(int memberNum, String memberId, String memberPw) {
        this.memberNum = memberNum;
        this.memberId = memberId;
        this.memberPw = memberPw;
    }

    // INSERT VO 생성 용도
    public MemberVo(String memberId, String memberPw, String memberEmail, Date memberBirth, String memberNickname) {
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberEmail = memberEmail;
        this.memberBirth = memberBirth;
        this.memberNickname = memberNickname;
        this.memberRegistrationDate = TimeUtility.getCurrentTimestamp();
        this.memberExist = 1;
        this.memberTypeNum = 2;
    }
}
