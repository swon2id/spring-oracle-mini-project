package com.kh.jdbc_oracle_spring.common;

import com.kh.jdbc_oracle_spring.vo.PostVo;
import java.util.List;

public class PostUtility {
    public static List<PostVo> setPostsWithMemberNameAndUrl(List<PostVo> posts) {
        // memberName을 조인을 통해 업데이트
        for (PostVo vo : posts) {
            vo.setPostAuthorName("운영자" + vo.getMemberNum());
            String boardType = vo.getBoardNum() == 0 ? "notice" : "general";
            vo.setPostUrl("/board/" + boardType + "/post/" + vo.getPostNum());
        }
        return posts;
    }
}
