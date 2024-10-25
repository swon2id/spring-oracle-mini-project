package com.kh.jdbc_oracle_spring.vo;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString // ?
public class ReplyVo {
    // 테이블 구조
    private int replyNum;
    private String replyContent;
    private Date replyPublishedDate;
    private int replyLikeCount;
    private int replyDislikeCount;
    private int memberNum;
    private int postNum;

    public ReplyVo(int replyNum, String replyContent, Date replyPublishedDate, int replyLikeCount, int replyDislikeCount, int memberNum, int postNum) {
        this.replyNum = replyNum;
        this.replyContent = replyContent;
        this.replyPublishedDate = replyPublishedDate;
        this.replyLikeCount = replyLikeCount;
        this.replyDislikeCount = replyDislikeCount;
        this.memberNum = memberNum;
        this.postNum = postNum;
    }

    // 조인되는 속성
    private String replyAuthorName = null;
}
