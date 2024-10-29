package com.kh.jdbc_oracle_spring.vo;

import com.kh.jdbc_oracle_spring.dao.ReplyEvaluationDao;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReplyEvaluationVo {
    private Integer memberNum;
    private Integer replyNum;
    private Integer replyEvaluationType;

    public ReplyEvaluationVo(int memberNum, int replyNum, int replyEvaluationType) {
        this.memberNum = memberNum;
        this.replyNum = replyNum;
        this.replyEvaluationType = replyEvaluationType;
    }

    public final static int LIKE = 1;
    public final static int DISLIKE = 0;
}
