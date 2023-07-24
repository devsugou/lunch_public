package com.station.lunch.dto;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 店舗情報のモデルクラス
 */
@Data
public class StoreDto {

    /** 店舗ID */
    private int storeId;

    /** 店舗名 */
    private String storeName;

    /** 店舗の電話番号 */
    private String storePhoneNumber;

    /** 店舗の住所 */
    private String storeAddress;

    /** 店舗のURL */
    private String storeUrl;

    /** 店舗の営業日 */
    private String storeBusinessDay;

    /** 店舗の営業時間 */
    private String storeBusinessHours;

    /** 店舗の定休日 */
    private String storeRegularClosingDay;

    /** 店舗画像のデータ */
    private byte[] storeImageData;
    
    public String getEncodedImageData() {
        if (this.storeImageData != null) {
            return Base64.getEncoder().encodeToString(this.storeImageData);
        }
        return null;
    }

    /** 店舗の説明 */
    private String storeDescription;

    /** ジャンルID */
    private int genreId;

    /** ジャンル名 */
    private String genreName;

    /** 距離ID */
    private int distanceId;

    /** 距離名 */
    private String distanceName;

    /** 評価スターID */
    private int evaluationStarId;

    /** 評価スター数 */
    private Double evaluationStarCount;

    /** メニュー情報リスト */
    private List<MenuDetailDto> menuDetailInfoList;
    
    /** 作成者 */
    private String createUser;
    
    /** 作成プロセス */
    private String createProcess;
    
    /** 作成日 */
    private Date createDate;
    
    /** 更新者 */
    private String updateUser;
    
    /** 更新プロセス */
    private String updateProcess;
    
    /** 更新日 */
    private Date updateDate;
    
    /** 削除フラグ */
    private int deleteFlag;

}
