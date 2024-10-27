package com.kh.jdbc_oracle_spring.common;

import com.kh.jdbc_oracle_spring.vo.ReplyVo;

import java.util.List;

public class ReplyUtility {
    public static List<ReplyVo> setReplysWithMemberName(List<ReplyVo> replys) {
        // memberName을 조인을 통해 직접적으로 얻어오는 것으로 추후 제거할 것
        for (ReplyVo vo : replys) {
            vo.setReplyAuthorName("운영자" + vo.getMemberNum());
        }
        return replys;
    }
}
