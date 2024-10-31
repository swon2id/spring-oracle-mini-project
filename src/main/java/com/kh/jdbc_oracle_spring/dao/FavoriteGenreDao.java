package com.kh.jdbc_oracle_spring.dao;

import com.kh.jdbc_oracle_spring.vo.FavoriteGenreVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FavoriteGenreDao {
    private final JdbcTemplate jdbcTemplate;

    public FavoriteGenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Integer> selectGenreNumByMemberNum(int memberNum) {
        String sql = "SELECT GENRE_NUM FROM FAVORITE_GENRE WHERE MEMBER_NUM = " + memberNum;
        return jdbcTemplate.queryForList(sql, Integer.class);
    }

    public List<FavoriteGenreVo> selectWithGenreNameByMemberNum(int memberNum) {
        String sql = "SELECT fg.MEMBER_NUM, GENRE_NUM, g.GENRE_NAME FROM FAVORITE_GENRE fg JOIN GENRE g USING(GENRE_NUM) WHERE fg.MEMBER_NUM = " + memberNum;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new FavoriteGenreVo(
                rs.getInt("MEMBER_NUM"),
                rs.getInt("GENRE_NUM"),
                rs.getString("GENRE_NAME")
        ));
    }

    public void insert(FavoriteGenreVo vo) {
        String sql = "INSERT INTO FAVORITE_GENRE VALUES(?, ?)";
        jdbcTemplate.update(sql, vo.getMemberNum(), vo.getGenreNum());
    }

    public void deleteByMemberNum(int memberNum) {
        String sql = "DELETE FROM FAVORITE_GENRE WHERE MEMBER_NUM = ?";
        jdbcTemplate.update(sql, memberNum);
    }

    public void deleteByMemberNumAndGenreNum(int memberNum, int genreNum) {
        String sql = "DELETE FROM FAVORITE_GENRE WHERE MEMBER_NUM = ? AND GENRE_NUM = ?";
        jdbcTemplate.update(sql, memberNum, genreNum);
    }
}
