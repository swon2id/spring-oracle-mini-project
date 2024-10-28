package com.kh.jdbc_oracle_spring.common;

import com.kh.jdbc_oracle_spring.vo.FavoriteGenreVo;
import com.kh.jdbc_oracle_spring.vo.GenreVo;

import java.util.List;
import java.util.stream.Collectors;

public class FavoriteGenreUtility {
    public static int getGenreNumToDelete(List<FavoriteGenreVo> favoriteGenres, List<String> selectedGenres) {
        for (FavoriteGenreVo vo: favoriteGenres) {
            if (vo.getGenreName() != null && !selectedGenres.contains(vo.getGenreName())) {
                return vo.getGenreNum();
            }
        }
        return -1;
    }

    public static int getGenreNumToInsert(List<GenreVo> genres, List<FavoriteGenreVo> favoriteGenres, List<String> selectedGenres) {
        for (String genreName : selectedGenres) {
            boolean existsInFavorites = favoriteGenres.stream()
                    .anyMatch(vo -> genreName.equals(vo.getGenreName()));

            if (!existsInFavorites) {
                return genres.stream()
                        .filter(genre -> genreName.equals(genre.getGenreName()))
                        .map(GenreVo::getGenreNum)
                        .findFirst()
                        .orElse(-1);
            }
        }
        return -1;
    }
}
