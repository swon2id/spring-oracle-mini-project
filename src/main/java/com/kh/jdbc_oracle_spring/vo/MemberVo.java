package com.kh.jdbc_oracle_spring.vo;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberVo {
    private int memberNum;
    private String memberId;
    private String memberPw;
    private String memberEmail;
    private Date memberBirth;
    private String memberNickname;
    private Date memberRegistrationDate;
    private int memberExist;
    private int memberTypeNum;
}
