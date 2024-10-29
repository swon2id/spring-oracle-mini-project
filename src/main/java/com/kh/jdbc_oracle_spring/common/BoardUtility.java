package com.kh.jdbc_oracle_spring.common;

import com.kh.jdbc_oracle_spring.vo.PostVo;

public class BoardUtility {
    private static final int NOTICE_BOARD_NUM = PostVo.NOTICE_BOARD_NUM;
    private static final int GENERAL_BOARD_NUM = PostVo.GENERAL_BOARD_NUM;
    private static final String NOTICE_BOARD_NAME = "공지게시판";
    private static final String GENERAL_BOARD_NAME = "자유게시판";

    public static boolean isValidBoardType(String boardType) {
        return getBoardNum(boardType) != -1;
    }

    public static int getBoardNum(String boardType) {
        switch (boardType) {
            case "notice":
                return NOTICE_BOARD_NUM;
            case "general":
                return GENERAL_BOARD_NUM;
            default:
                return -1;
        }
    }

    public static String getBoardName(String boardType) {
        switch (boardType) {
            case "notice":
                return NOTICE_BOARD_NAME;
            case "general":
                return GENERAL_BOARD_NAME;
            default:
                return "게시판";
        }
    }
}
