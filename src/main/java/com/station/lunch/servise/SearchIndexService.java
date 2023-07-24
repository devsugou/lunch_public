package com.station.lunch.servise;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.station.lunch.dto.MenuDetailDto;
import com.station.lunch.dto.StoreDto;
import com.station.lunch.form.SearchForm;
import com.station.lunch.mapper.SearchIndexMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchIndexService {

    @Autowired
    private SearchIndexMapper searchIndexMapper;
    
    @Autowired
    private MessageSource messageSource;

    /**
     * 店舗情報一覧、メニュー情報をFormクラスに格納する
     * @param searchIndexForm
     * @return
     */
    public SearchForm getStores(SearchForm searchForm) throws RuntimeException{
        List<StoreDto> storeDtoList = null;
        try {
            // 店舗情報一覧を取得
            storeDtoList = searchIndexMapper.selectStore(
            		searchForm.getSearchkeyword(),
            		searchForm.getSearchGenreId(),
            		searchForm.getSearchEvaluationStarCount(),
            		searchForm.getSearchDistanceId());
            for (StoreDto storeDto: storeDtoList) {
                // 店舗のメニュー情報を取得
                List<MenuDetailDto> menuDetailInfoList = searchIndexMapper.selectMenu(storeDto.getStoreId());
                // 店舗情報一覧にメニュー情報を格納
                storeDto.setMenuDetailInfoList(menuDetailInfoList);
            }
        } catch (Exception e) {
        	String errorMessage = messageSource.getMessage("error.retrieving.store", null, Locale.JAPAN);
            // Log error message
            log.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    
        // It's also useful to log successful operations for auditing purposes
        if (storeDtoList != null) {
            String infoMessage = messageSource.getMessage("info.retrieved.store", new Object[]{storeDtoList.size()}, Locale.JAPAN);
            log.info(infoMessage);
            //店舗情報一覧
            searchForm.setStoreDtoList(storeDtoList);
            //店舗情報一覧の件数
            searchForm.setTotalStoresCount(storeDtoList.size());
        }
        return searchForm;
    }
    /**
     * 店舗情報を取得する
     * @param storeId
     * @return
     */
    public List<MenuDetailDto> getMenus(int storeId) throws RuntimeException{
        List<MenuDetailDto> menuDetailInfoList = null;
        try {
            // 店舗のメニュー情報を取得
            menuDetailInfoList = searchIndexMapper.selectMenu(storeId);
        } catch (Exception e) {
            String errorMessage = messageSource.getMessage("error.retrieving.menu", new Object[]{storeId}, Locale.JAPAN);
            log.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        return menuDetailInfoList;
    }
    
    /**
     * 店舗情報を削除する
     * @param storeId
     */
    public void deleteStore(Integer storeId) {
    	try {
    		searchIndexMapper.deleteStore(storeId);
    		 
        } catch (Exception e) {
            String errorMessage = messageSource.getMessage("error.deleting.store", new Object[]{storeId}, Locale.JAPAN);
            log.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        String infoMessage = messageSource.getMessage("info.deleted.store", null, Locale.JAPAN);
        log.info(infoMessage);
        
    }
}