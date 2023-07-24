package com.station.lunch.aop;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.station.lunch.servise.CommonService;
import com.station.lunch.session.SessionData;

import lombok.extern.slf4j.Slf4j;

/**
 * セッションデータを管理するためのAspectクラスです。
 * このクラスは、全てのコントローラーメソッドの前に実行され、セッションデータがnullであれば新たに取得し、セッションに保存します。
 */
@Aspect
@Component
@Slf4j
public class SessionDataAspect {

    @Autowired
    private HttpSession session;

    @Autowired
    private CommonService commonService;
    
	@Autowired
	private MessageSource messageSource;

    /**
     * このメソッドは、全てのコントローラーメソッドの前に実行されます。
     * セッションからsessionDataを取得し、それがnull（つまりセッションが切れている）の場合は新たにセッションデータを取得してセッションに保存します。
     */
    @Before("execution(* com.station.lunch.controller..*(..))")
    public void ensureSessionData() {
        SessionData sessionData = (SessionData) session.getAttribute("sessionData");
        if (sessionData == null || 
        	    (sessionData.getGenreList() == null && 
        	    sessionData.getEvaluationStarMap() == null &&
        	    sessionData.getDistanceList() == null && 
        	    sessionData.getEvaluationStarList() == null && 
        	    sessionData.getDaysOfWeek() == null)) {
        	try {
        		sessionData = commonService.getSessionData();
        		session.setAttribute("sessionData", sessionData);
        	} catch (Exception e) {
        		log.info(messageSource.getMessage("error.session.create", null, Locale.JAPAN));
        	}
        }
    }
}
