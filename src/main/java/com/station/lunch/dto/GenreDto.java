package com.station.lunch.dto;

import lombok.Data;

/**
 * ジャンル情報を保持するモデルクラス
 */
@Data
public class GenreDto {

    /** ジャンルID */
    private int genreId;

    /** ジャンル名称 */
    private String genreName;
}
