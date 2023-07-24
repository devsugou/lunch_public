package com.station.lunch.dto;

import lombok.Data;

/**
 * 新宿パークタワーからの距離情報を保持するモデルクラス
 */
@Data
public class DistanceDto {
    
    /** 距離ID */
    private int distanceId;
    
    /** 距離名称 */
    private String distanceName;    
}
