package com.station.lunch.servise;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.station.lunch.dto.StoreDto;
import com.station.lunch.exception.ImageProcessingException;
import com.station.lunch.form.StoreForm;
import com.station.lunch.mapper.SearchRegisterMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoreRegisterService {
	
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private SearchRegisterMapper searchRegisterMapper;
    
    /**
     * 
     * @param storeId
     * @return
     */
    public StoreForm getStore(Long storeId) {
        StoreDto storeDto = searchRegisterMapper.selectStore(storeId);

        // Convert StoreDto to StoreForm
        StoreForm storeForm = new StoreForm();
        
        storeForm.setStoreId(storeDto.getStoreId());
        storeForm.setStoreName(storeDto.getStoreName());
        storeForm.setStorePhoneNumber(storeDto.getStorePhoneNumber());
        storeForm.setStoreAddress(storeDto.getStoreAddress());
        storeForm.setStoreUrl(storeDto.getStoreUrl());
        storeForm.setStoreBusinessDay(storeDto.getStoreBusinessDay());
        storeForm.setStoreBusinessDaysList(Arrays.asList(storeDto.getStoreBusinessDay().split(",")));
        // storeBusinessHoursを開始時間と終了時間に分割する。
        String[] businessHours = storeDto.getStoreBusinessHours().split(" - ");
        if (businessHours.length == 2) {
            storeForm.setStoreBusinessStartHours(businessHours[0]);
            storeForm.setStoreBusinessEndHours(businessHours[1]);
        }
        storeForm.setStoreRegularClosingDay(storeDto.getStoreRegularClosingDay());
        // storeForm.setStoreImageData(storeDto.getStoreImageData());
        storeForm.setStoreDescription(storeDto.getStoreDescription());
        storeForm.setGenreId(storeDto.getGenreId());
        storeForm.setDistanceId(storeDto.getDistanceId());
        storeForm.setEvaluationStarId(storeDto.getEvaluationStarId());
        storeForm.setEvaluationStarCount(storeDto.getEvaluationStarCount());
        storeForm.setMenuDetailInfoList(storeDto.getMenuDetailInfoList());
        
        return storeForm;
    }
    
    /**
     * 店舗情報の更新
     * @param storeForm
     * @throws ImageProcessingException 
     */
    @Transactional
    public void updateStore(StoreForm storeForm) throws RuntimeException, ImageProcessingException {
        
    	// 現在の日時を取得
        Date currentDate = new Date();
        
        // StoreFormからStoreDtoへの変換
        StoreDto storeDto = new StoreDto();
        //画像をByteに変換
        byte[] storeImageData;
        if (storeForm.getStoreImageData() != null) {
	        try {
	        	storeImageData = storeForm.getStoreImageData().getBytes();
	        } catch (IOException e) {
	            log.error("画像データのバイト配列への変換に失敗しました。", e);
	            throw new ImageProcessingException("画像データのバイト配列への変換に失敗しました。", e);
	        }
        }else {
        	storeImageData = null;
        }
        try {
            storeDto.setStoreId(storeForm.getStoreId());
            storeDto.setStoreName(storeForm.getStoreName());
            storeDto.setStorePhoneNumber(storeForm.getStorePhoneNumber());
            storeDto.setStoreAddress(storeForm.getStoreAddress());
            storeDto.setStoreUrl(storeForm.getStoreUrl());
            storeDto.setStoreBusinessDay(storeForm.getStoreBusinessDay());
            String storeBusinessHours;
            if (storeForm.getStoreBusinessStartHours() != null && storeForm.getStoreBusinessEndHours() != null) {
            	storeBusinessHours = storeForm.getStoreBusinessStartHours() + " - " + storeForm.getStoreBusinessEndHours();
            }else {
            	storeBusinessHours =  null;
            }
            storeDto.setStoreBusinessHours(storeBusinessHours);
            storeDto.setStoreRegularClosingDay(storeForm.getStoreRegularClosingDay());
            storeDto.setStoreImageData(storeImageData);
            storeDto.setStoreDescription(storeForm.getStoreDescription());
            storeDto.setGenreId(storeForm.getGenreId());
            storeDto.setDistanceId(storeForm.getDistanceId());
            storeDto.setEvaluationStarId(storeForm.getEvaluationStarId());
            storeDto.setEvaluationStarCount(storeForm.getEvaluationStarCount());
            storeDto.setMenuDetailInfoList(storeForm.getMenuDetailInfoList());

            storeDto.setUpdateUser("ADMIN");
            storeDto.setUpdateProcess("ADMIN");
            storeDto.setUpdateDate(currentDate);

            searchRegisterMapper.updateStore(storeDto);
        } catch (Exception e) {
            String errorMessage = messageSource.getMessage("error.updating.store", null, Locale.JAPAN);
            log.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }

        String infoMessage = messageSource.getMessage("info.update.store", null, Locale.JAPAN);
        log.info(infoMessage);
    }
    /**
     * 店舗情報の新規登録
     * @param storeForm 店舗フォーム
     * @throws ImageProcessingException 
     */
    @Transactional
    public void saveStore(StoreForm storeForm) throws ImageProcessingException {
    	// 現在の日時を取得
        Date currentDate = new Date();
        
        // StoreFormからStoreDtoへの変換
        StoreDto storeDto = new StoreDto();
        //画像をByteに変換
        byte[] storeImageData;
        if (storeForm.getStoreImageData() != null) {
	        try {
	        	storeImageData = storeForm.getStoreImageData().getBytes();
	        } catch (IOException e) {
				String statusMessage = messageSource.getMessage("error.store.register.image.conversion", null, Locale.JAPAN);
				log.error(statusMessage, e);
	            throw new ImageProcessingException(statusMessage, e);
	        }
        }else {
        	storeImageData = null;
        }
        try {
            storeDto.setStoreId(storeForm.getStoreId());
            storeDto.setStoreName(storeForm.getStoreName());
            storeDto.setStorePhoneNumber(storeForm.getStorePhoneNumber());
            storeDto.setStoreAddress(storeForm.getStoreAddress());
            storeDto.setStoreUrl(storeForm.getStoreUrl());
            storeDto.setStoreBusinessDay(storeForm.getStoreBusinessDay());
            String storeBusinessHours;
            if (storeForm.getStoreBusinessStartHours() != null && storeForm.getStoreBusinessEndHours() != null) {
            	storeBusinessHours = storeForm.getStoreBusinessStartHours() + " - " + storeForm.getStoreBusinessEndHours();
            }else {
            	storeBusinessHours =  null;
            }
            storeDto.setStoreBusinessHours(storeBusinessHours);
            storeDto.setStoreRegularClosingDay(storeForm.getStoreRegularClosingDay());
            storeDto.setStoreImageData(storeImageData);
            storeDto.setStoreDescription(storeForm.getStoreDescription());
            storeDto.setGenreId(storeForm.getGenreId());
            storeDto.setDistanceId(storeForm.getDistanceId());
            storeDto.setEvaluationStarId(storeForm.getEvaluationStarId());
            storeDto.setEvaluationStarCount(storeForm.getEvaluationStarCount());
            storeDto.setMenuDetailInfoList(storeForm.getMenuDetailInfoList());

            storeDto.setCreateUser("ADMIN");
            storeDto.setCreateProcess("ADMIN");
            storeDto.setCreateDate(currentDate);
            storeDto.setUpdateUser("ADMIN");
            storeDto.setUpdateProcess("ADMIN");
            storeDto.setUpdateDate(currentDate);
            storeDto.setDeleteFlag(0);

            searchRegisterMapper.insertStore(storeDto);
        } catch (Exception e) {
            String errorMessage = messageSource.getMessage("error.saving.store", null, Locale.JAPAN);
            log.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }

        String infoMessage = messageSource.getMessage("info.saved.store", null, Locale.JAPAN);
        log.info(infoMessage);
    }
}
