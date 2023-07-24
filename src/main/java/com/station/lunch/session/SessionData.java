package com.station.lunch.session;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.station.lunch.dto.DistanceDto;
import com.station.lunch.dto.EvaluationDto;
import com.station.lunch.dto.GenreDto;

import lombok.Data;

/**
 * セッションデータを保持するモデルクラス
 */
@Data
public class SessionData implements Serializable {
    private transient List<GenreDto> genreList;
    private transient Map<Integer, String> evaluationStarMap;
    private transient List<DistanceDto> distanceList;
    private transient List<EvaluationDto> evaluationStarList;
    private transient List<String> daysOfWeek;

}
