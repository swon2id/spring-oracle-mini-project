package com.kh.jdbc_oracle_spring.dao;

import com.kh.jdbc_oracle_spring.common.WebtoonUtility;
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

    public WebtoonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WebtoonVo> selectByGenreAndDayOfWeek(int platformNum, List<String>selectedGenres, int dayOfWeek) {
        return WebtoonUtility.getDummyList(platformNum, selectedGenres, dayOfWeek);
    }

    public List<WebtoonVo> selectByPlatform(int platformNum) {
        return WebtoonUtility.getTop10List(WebtoonUtility.getDummyList(platformNum, null, -1));
    }

    // null을 가질 수 있는 컬럼 값 때문에 테스트 필요
    private static class WebtoonRowMapper implements RowMapper<WebtoonVo> {
        @Override
        public WebtoonVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new WebtoonVo(
                rs.getInt(WebtoonVo.WEBTOON_NUM_STR),
                rs.getString(WebtoonVo.WEBTOON_TITLE_STR),
                rs.getString(WebtoonVo.WEBTOON_AUTHOR_STR),
                rs.getInt(WebtoonVo.GENRE_NUM_STR),
                rs.getInt(WebtoonVo.WEBTOON_AVAILABLE_AGE_STR),
                rs.getDouble(WebtoonVo.WEBTOON_RATING_STR),
                rs.getObject(WebtoonVo.WEBTOON_SUBSCRIBE_COUNT_STR, Integer.class), // null 처리
                rs.getObject(WebtoonVo.WEBTOON_VIEW_COUNT_STR, Integer.class), // null 처리
                rs.getInt(WebtoonVo.WEBTOON_RELEASE_DAY_STR),
                rs.getString(WebtoonVo.WEBTOON_PAGE_URL_STR),
                rs.getString(WebtoonVo.WEBTOON_THUMBNAIL_URL_STR),
                rs.getInt(WebtoonVo.PLATFORM_NUM_STR)
            );
        }
    }
}
