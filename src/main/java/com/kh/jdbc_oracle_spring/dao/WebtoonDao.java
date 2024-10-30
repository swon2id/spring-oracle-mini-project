package com.kh.jdbc_oracle_spring.dao;

import com.kh.jdbc_oracle_spring.vo.WebtoonVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WebtoonDao {
    private final JdbcTemplate jdbcTemplate;

    public WebtoonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WebtoonVo> selectByGenreAndReleaseDay(int platformNum, List<Integer>selectedGenres, int releaseDay) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM WEBTOON WHERE (");
        for (int genre: selectedGenres) sqlBuilder.append("GENRE_NUM = " + genre + " OR ");
        sqlBuilder.delete(sqlBuilder.length() - 3, sqlBuilder.length());
        sqlBuilder.append(") AND PLATFORM_NUM = " + platformNum + " AND WEBTOON_RELEASE_DAY = " + releaseDay);
        return jdbcTemplate.query(sqlBuilder.toString(), new WebtoonRowMapper());
    }

    public List<WebtoonVo> selectByReleaseDay(int platformNum, int releaseDay) {
        String sql = "SELECT * FROM WEBTOON WHERE PLATFORM_NUM = " + platformNum
                + " AND WEBTOON_RELEASE_DAY = " + releaseDay;
        return jdbcTemplate.query(sql, new WebtoonRowMapper());
    }

    public List<WebtoonVo> selectByPlatform(int platformNum) {
        String sql = "SELECT * FROM WEBTOON WHERE PLATFORM_NUM = " + platformNum;
        return jdbcTemplate.query(sql, new WebtoonRowMapper());
    }

    public List<WebtoonVo> selectWithGenreNameByTerm(String term) {
        String sql = "SELECT *"
                + " FROM WEBTOON w"
                + " JOIN GENRE g"
                + " USING(GENRE_NUM)"
                + " WHERE w.WEBTOON_TITLE LIKE '%" + term + "%'"
                + " OR w.WEBTOON_AUTHOR LIKE '%" + term + "%'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new WebtoonVo(
            rs.getInt("WEBTOON_NUM"),
            rs.getString("WEBTOON_TITLE"),
            rs.getString("WEBTOON_AUTHOR"),
            rs.getInt("GENRE_NUM"),
            rs.getInt("WEBTOON_AVAILABLE_AGE"),
            rs.getDouble("WEBTOON_RATING"),
            rs.getObject("WEBTOON_SUBSCRIBE_COUNT", Integer.class), // null 처리
            rs.getObject("WEBTOON_VIEW_COUNT", Integer.class), // null 처리
            rs.getInt("WEBTOON_RELEASE_DAY"),
            rs.getString("WEBTOON_PAGE_URL"),
            rs.getString("WEBTOON_THUMBNAIL_URL"),
            rs.getInt("PLATFORM_NUM"),
            rs.getString("GENRE_NAME")
        ));
    }

    // null을 가질 수 있는 컬럼 값 때문에 테스트 필요
    private static class WebtoonRowMapper implements RowMapper<WebtoonVo> {
        @Override
        public WebtoonVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new WebtoonVo(
                rs.getInt("WEBTOON_NUM"),
                rs.getString("WEBTOON_TITLE"),
                rs.getString("WEBTOON_AUTHOR"),
                rs.getInt("GENRE_NUM"),
                rs.getInt("WEBTOON_AVAILABLE_AGE"),
                rs.getDouble("WEBTOON_RATING"),
                rs.getObject("WEBTOON_SUBSCRIBE_COUNT", Integer.class), // null 처리
                rs.getObject("WEBTOON_VIEW_COUNT", Integer.class), // null 처리
                rs.getInt("WEBTOON_RELEASE_DAY"),
                rs.getString("WEBTOON_PAGE_URL"),
                rs.getString("WEBTOON_THUMBNAIL_URL"),
                rs.getInt("PLATFORM_NUM")
            );
        }
    }
}
