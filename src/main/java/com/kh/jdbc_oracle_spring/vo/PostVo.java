package com.kh.jdbc_oracle_spring.vo;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString // ?
public class PostVo {
    // 테이블 구조
    private int postNum;
    private String postTitle;
    private String postContent;
    private Date postPublishedDate;
    private int memberNum;
    private int boardNum;
    private int postVisit = 0;

    public PostVo(int postNum, String postTitle, String postContent, Date postPublishedDate, int memberNum, int boardNum) {
        this.postNum = postNum;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postPublishedDate = postPublishedDate;
        this.memberNum = memberNum;
        this.boardNum = boardNum;
    }

    // 조인을 통해 자주 사용하는 값
    private String postAuthorName = null;
    private String postUrl = null;
}
