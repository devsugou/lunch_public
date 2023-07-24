package com.station.lunch.servise;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.station.lunch.dto.DistanceDto;
import com.station.lunch.dto.EvaluationDto;
import com.station.lunch.dto.GenreDto;
import com.station.lunch.mapper.CommonMapper;
import com.station.lunch.session.SessionData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommonService {

    @Autowired
    private CommonMapper commonMapper;

    /**
     * 検索用のプルダウン情報を取得する
     * @return  セッションデータ
     */
    public SessionData getSessionData() {
        SessionData sessionData = new SessionData();
        List<GenreDto> genres = null;
        List<Integer> stars = null;
        List<DistanceDto> distances = null;
        List<EvaluationDto> starsInfo = null;
        
        try {
            genres = commonMapper.selectGenre();
            stars = commonMapper.selectEvaluationStarBySearch();
            distances = commonMapper.selectDistance();
            starsInfo = commonMapper.selectEvaluationStarByCreate();
            
        } catch (Exception e) {
            log.error("Error occurred while retrieving", e);
        }
        if (genres != null) {
            log.info("Successfully retrieved " + genres.size() + " genres");
        }
        if (stars != null) {
            log.info("Successfully retrieved " + stars.size() + " evaluation stars");
        }
        if (distances != null) {
            log.info("Successfully retrieved " + distances.size() + " distances");
        }
        if (starsInfo != null) {
            log.info("Successfully retrieved " + starsInfo.size() + "  evaluation stars");
        }
        
        //ジャンル情報を設定
        sessionData.setGenreList(genres);
        
        //評価情報を設定
        Map<Integer, String> evaluationStarMap = new LinkedHashMap<>();
        for (int star :stars) {
        evaluationStarMap.put(star, "★".repeat(star) + "☆".repeat(stars.get(stars.size()-1)-star));
        }
        sessionData.setEvaluationStarMap(evaluationStarMap);
        
        //距離情報を設定
        sessionData.setDistanceList(distances);
        
        //登録用評価点数を設定
        sessionData.setEvaluationStarList(starsInfo);
        
        //曜日情報を設定
        List<String> daysOfWeek = Arrays.asList("日", "月", "火", "水", "木", "金", "土");
        sessionData.setDaysOfWeek(daysOfWeek);
        
        return sessionData;
        
    }

}
