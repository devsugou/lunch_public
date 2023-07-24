package com.station.lunch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.station.lunch.dto.MenuDetailDto;
import com.station.lunch.dto.StoreDto;
import com.station.lunch.form.StoreForm;

/**
 * 店舗情報一覧、メニュー情報一覧・検索のMapperインターフェース
 */
@Mapper
public interface SearchIndexMapper {
    
    /**
     * 店舗一覧を検索・取得する
     * @param searchkeyword 検索キーワード     
     * @param searchGenreId　ジャンルID
     * @param searchEvaluationStarCount　星の数
     * @param searchDistanceId  距離ID    
     * @return  店舗情報リスト
     */
    public List<StoreDto> selectStore(
        @Param("keyword") String searchkeyword,
        @Param("genreId") Integer searchGenreId,
        @Param("evaluationStarCount") Integer searchEvaluationStarCount,
        @Param("distanceId") Integer searchDistanceId);

    /**
     * メニュー情報を取得する
     * @param storeId  店舗ID
     * @return メニュー情報リスト
     */
    public List<MenuDetailDto> selectMenu(
        @Param("storeId") int storeId);
    
    /**
     * 店舗情報を削除する
     * @param storeId
     */
    public void deleteStore(@Param("storeId") int storeId);
}   
