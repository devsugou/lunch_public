package com.station.lunch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.station.lunch.dto.DistanceDto;
import com.station.lunch.dto.EvaluationDto;
import com.station.lunch.dto.GenreDto;

/**
 * 共通のMapperインターフェース
 */
@Mapper
public interface CommonMapper {
    
    /**
     * ジャンル情報を取得する
     * @return ジャンル情報リスト
     */
    public List<GenreDto> selectGenre();

    /**
     * 検索用評価情報を取得する
     * @return 評価情報リスト
     */
    public List<Integer> selectEvaluationStarBySearch();

    /**
     * 距離情報を取得する
     * @return 距離情報リスト
     */
    public List<DistanceDto> selectDistance();
    
    /**
     * 登録用評価情報を取得する
     * @return 評価情報リスト
     */
    public List<EvaluationDto> selectEvaluationStarByCreate();    

}
