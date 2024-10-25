package com.kh.jdbc_oracle_spring.common;

import com.kh.jdbc_oracle_spring.vo.ReplyVo;

import java.util.List;

public class ReplyUtility {
    public static List<ReplyVo> setReplysWithMemberName(List<ReplyVo> replys) {
        // memberName을 조인을 통해 업데이트
        for (ReplyVo vo : replys) {
            vo.setReplyAuthorName("운영자" + vo.getMemberNum());
        }
        return replys;
    }
}
