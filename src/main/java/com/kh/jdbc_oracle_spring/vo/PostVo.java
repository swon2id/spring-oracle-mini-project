package com.kh.jdbc_oracle_spring.vo;

import com.kh.jdbc_oracle_spring.common.TimeUtility;
import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString // ?
public class PostVo {
    private Integer postNum;
    private String postTitle;
    private String postContent;
    private Timestamp postPublishedDate;
    private Integer memberNum;
    private Integer boardNum;
    private Integer postVisit = 0;

    // INSERT VO 생성 용도
    public PostVo(String postTitle, String postContent, int memberNum, int boardNum) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postPublishedDate = TimeUtility.getCurrentTimestamp();
        this.memberNum = memberNum;
        this.boardNum = boardNum;
    }

    // MEMBER 테이블 조인 맵핑 용도
    public PostVo(int postNum, String postTitle, String postContent, Timestamp postPublishedDate, int memberNum, int postVisit, int boardNum, String memberNickname) {
        this.postNum = postNum;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postPublishedDate = postPublishedDate;
        this.memberNum = memberNum;
        this.postVisit = postVisit;
        this.boardNum = boardNum;
        this.memberNickname = memberNickname;
    }

    // 수정용
    public PostVo(String postTitle, String postContent) {
        this.postTitle = postTitle;
        this.postContent = postContent;
    }

    // MEMBER 테이블 조인 컬럼
    private String memberNickname = null;

    // 보드 종류
    public final static int NOTICE_BOARD_NUM = 1;
    public final static int GENERAL_BOARD_NUM = 2;
}
