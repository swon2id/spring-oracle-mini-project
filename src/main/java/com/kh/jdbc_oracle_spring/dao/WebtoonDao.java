package com.kh.jdbc_oracle_spring.dao;

import com.kh.jdbc_oracle_spring.vo.WebtoonVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WebtoonDao {
    private final JdbcTemplate jdbcTemplate;

    // SQL에 사용할 컬럼명 상수화
    private static final String WEBTOON_NUM_STR = "WEBTOON_NUM";
    private static final String WEBTOON_TITLE_STR = "WEBTOON_TITLE";
    private static final String WEBTOON_AUTHOR_STR = "WEBTOON_AUTHOR";
    private static final String GENRE_NUM_STR = "GENRE_NUM";
    private static final String WEBTOON_AVAILABLE_AGE_STR = "WEBTOON_AVAILABLE_AGE";
    private static final String WEBTOON_RATING_STR = "WEBTOON_RATING";
    private static final String WEBTOON_SUBSCRIBE_COUNT_STR = "WEBTOON_SUBSCRIBE_COUNT";
    private static final String WEBTOON_VIEW_COUNT_STR = "WEBTOON_VIEW_COUNT";
    private static final String WEBTOON_RELEASE_DAY_STR = "WEBTOON_RELEASE_DAY";
    private static final String WEBTOON_PAGE_URL_STR = "WEBTOON_PAGE_URL";
    private static final String WEBTOON_THUMBNAIL_URL_STR = "WEBTOON_THUMBNAIL_URL";
    private static final String PLATFORM_NUM_STR = "PLATFORM_NUM";

    public WebtoonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WebtoonVo> selectWebtoonByPlatform(int platformNum) {
        String sql = "SELECT * FROM WEBTOON WHERE " + PLATFORM_NUM_STR + " = ?;";
        // return jdbcTemplate.query(sql, new WebtoonRowMapper(), platformNum);

        // 임시 코드, 추후 실제 jdbc로 데이터베이스에서 값을 가져올 것
        List<WebtoonVo> list = new ArrayList<>();
        list.add(new WebtoonVo(1, "웹툰제목1", "웹툰작가1", 1, 0, 5.0, 1000, null, 0, "www.naver.com", "www.naver.com", 0));
        list.add(new WebtoonVo(2, "웹툰제목2", "웹툰작가2", 5, 12, 6.0, null, 100000, 5, "www.kakao.com", "www.kakao.com", 1));
        list.add(new WebtoonVo(3, "웹툰제목3", "웹툰작가3", 2, 15, 7.0, 1200, null, 3, "www.naver.com", "www.naver.com", 0));
        return list;
    }
    public List<WebtoonVo> selectWebtoonByPlatform(int platformNum, int dayOfWeek) {
        String sql = "SELECT * FROM WEBTOON WHERE " + PLATFORM_NUM_STR + " = ?;";
        // return jdbcTemplate.query(sql, new WebtoonRowMapper(), platformNum);

        // 임시 코드, 추후 실제 jdbc로 데이터베이스에서 값을 가져올 것
        List<WebtoonVo> list = new ArrayList<>();
        list.add(new WebtoonVo(1, "웹툰제목1", "웹툰작가1", 1, 0, 5.0, 1000, null, 0, "www.naver.com", "www.naver.com", 0));
        list.add(new WebtoonVo(2, "웹툰제목2", "웹툰작가2", 5, 12, 6.0, null, 100000, 5, "www.kakao.com", "www.kakao.com", 1));
        list.add(new WebtoonVo(3, "웹툰제목3", "웹툰작가3", 2, 15, 7.0, 1200, null, 3, "www.naver.com", "www.naver.com", 0));
        return list;
    }

    // null을 가질 수 있는 값 때문에 테스트 필요
    private static class WebtoonRowMapper implements RowMapper<WebtoonVo> {
        @Override
        public WebtoonVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new WebtoonVo(
                rs.getInt(WEBTOON_NUM_STR),
                rs.getString(WEBTOON_TITLE_STR),
                rs.getString(WEBTOON_AUTHOR_STR),
                rs.getInt(GENRE_NUM_STR),
                rs.getInt(WEBTOON_AVAILABLE_AGE_STR),
                rs.getDouble(WEBTOON_RATING_STR),
                rs.getObject(WEBTOON_SUBSCRIBE_COUNT_STR, Integer.class), // null 처리
                rs.getObject(WEBTOON_VIEW_COUNT_STR, Integer.class), // null 처리
                rs.getInt(WEBTOON_RELEASE_DAY_STR),
                rs.getString(WEBTOON_PAGE_URL_STR),
                rs.getString(WEBTOON_THUMBNAIL_URL_STR),
                rs.getInt(PLATFORM_NUM_STR)
            );
        }
    }
}
