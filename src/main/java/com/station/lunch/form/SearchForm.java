package com.station.lunch.form;

import java.util.List;

import com.station.lunch.dto.DistanceDto;
import com.station.lunch.dto.GenreDto;
import com.station.lunch.dto.StoreDto;

import lombok.Data;

@Data
public class SearchForm {

    /** 【検索】キーワード */
    private String searchkeyword;
    
    /** 【検索】飲食店のジャンルID */
    private int searchGenreId;
    
    /** 【検索】★（評価）の数 */
    private int searchEvaluationStarCount;
    
    /** 【検索】新宿パークタワーからの距離 */
    private int searchDistanceId;

    /** 【選択】飲食店のジャンル名称 */
    private String selectedGenreName;

    /** ジャンル一覧リスト */
    private List<GenreDto> genreList;

    /** 評価スター数の一覧リスト */
    private List<Integer> evaluationStarList;

    /** 新宿パークタワーからの距離の一覧リスト */
    private List<DistanceDto> distanceList;

    /** 店舗情報一覧リスト */
    private List<StoreDto> storeDtoList;

    /** 店舗情報一覧の件数 */
    private int totalStoresCount;
}
