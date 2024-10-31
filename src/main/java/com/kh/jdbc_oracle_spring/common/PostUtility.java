package com.kh.jdbc_oracle_spring.common;

import com.kh.jdbc_oracle_spring.vo.PostVo;
import java.util.ArrayList;
import java.util.List;

public class PostUtility {
    private static final int ADMIN_MEMBER_TYPE = 1;

    public static List<String> getPostUrl(List<PostVo> posts) {
        String[] boardName = {"", "notice", "general"};
        List<String> postUrls = new ArrayList<>();
        for (PostVo post: posts) {
            String url = boardName[post.getBoardNum()] + "/post/" + post.getPostNum();
            postUrls.add(url);
        }

        return postUrls.isEmpty() ? new ArrayList<>() : postUrls;
    }

    public static boolean isPostWritableMember(String boardType, int memberType) {
        if (!MemberUtility.isLoggedIn()) return false;
        if (boardType.equals("notice")) {
            // 공지게시판의 경우 관리자만 editable
            return memberType == ADMIN_MEMBER_TYPE;
        } else {
            return true;
        }
    }

    public static boolean isPostAuthor(int postAutuor, Integer memberNum) {
        if (!MemberUtility.isLoggedIn()) return false;
        return postAutuor == memberNum;
    }
}