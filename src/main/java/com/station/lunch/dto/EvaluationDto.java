package com.station.lunch.dto;

import lombok.Data;

/**
 * 評価情報を保持するモデルクラス
 */
@Data
public class EvaluationDto {

    /** 評価ID */
    private int evaluationStarId;

    /** 評価点数 */
    private Double evaluationStarCount;
}
