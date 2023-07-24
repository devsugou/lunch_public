package com.station.lunch.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * メニュー情報を保持するモデルクラス
 */
@Data
public class MenuDetailDto {

    /** メニューID */
    private String menuId;

    /** メニュー名称 */
    private String menuName;
    
    /** メニューの価格 */
    private BigDecimal menuPrice;
    
    /** メニューの画像ディレクトリパス */
    private String menuImageUrl;

    /** 店舗ID */
    private String storeid;    
}
