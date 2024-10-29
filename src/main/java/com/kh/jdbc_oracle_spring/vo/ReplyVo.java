package com.kh.jdbc_oracle_spring.vo;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString // ?
public class ReplyVo {
    private Integer replyNum;
    private String replyContent;
    private Timestamp replyPublishedDate;
    private Integer replyLikeCount;
    private Integer replyDislikeCount;
    private Integer memberNum;
    private Integer postNum;

    // INSERT VO 생성 용도
    public ReplyVo(String replyContent, Timestamp replyPublishedDate, int memberNum, int postNum) {
        this.replyContent = replyContent;
        this.replyPublishedDate = replyPublishedDate;
        this.replyLikeCount = 0;
        this.replyDislikeCount = 0;
        this.memberNum = memberNum;
        this.postNum = postNum;
    }

    // MEMBER 테이블 조인 맵핑 용도
    public ReplyVo(int replyNum, String replyContent, Timestamp replyPublishedDate, int replyLikeCount, int replyDislikeCount, int postNum, String memberNickname) {
        this.replyNum = replyNum;
        this.replyContent = replyContent;
        this.replyPublishedDate = replyPublishedDate;
        this.replyLikeCount = replyLikeCount;
        this.replyDislikeCount = replyDislikeCount;
        this.postNum = postNum;
        this.memberNickname = memberNickname;
    }

    // MEMBER 테이블 조인 컬럼
    private String memberNickname = null;
}
