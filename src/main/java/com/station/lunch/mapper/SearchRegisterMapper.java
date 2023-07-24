package com.station.lunch.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.station.lunch.dto.StoreDto;

/**
 * 店舗情報登録、更新のMapperインターフェース
 */
@Mapper
public interface SearchRegisterMapper {
	
    /**
     * 店舗情報を登録する
     * @param storeForm
     */
    public void insertStore(StoreDto storeDto) ;
    
    /**
     * 店舗情報の取得
     * @param storeId 店舗ID
     * @return 店舗情報
     */
    StoreDto selectStore(Long storeId);

    /**
     * 店舗情報の更新
     * @param storeDto 店舗情報
     */
    void updateStore(StoreDto storeDto);

}
