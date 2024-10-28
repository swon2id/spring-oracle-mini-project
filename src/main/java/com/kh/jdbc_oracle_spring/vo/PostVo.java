package com.kh.jdbc_oracle_spring.vo;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString // ?
public class PostVo {
    private Integer postNum;
    private String postTitle;
    private String postContent;
    private Date postPublishedDate;
    private Integer memberNum;
    private Integer boardNum;
    private Integer postVisit = 0;

    public PostVo(int postNum, String postTitle, String postContent, Date postPublishedDate, int memberNum, int boardNum) {
        this.postNum = postNum;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postPublishedDate = postPublishedDate;
        this.memberNum = memberNum;
        this.boardNum = boardNum;
    }

    // 임시 조인 컬럼
    private String postAuthorName = null;
    private String postUrl = null;
}
