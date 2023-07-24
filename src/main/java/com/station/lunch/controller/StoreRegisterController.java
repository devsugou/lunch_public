package com.station.lunch.controller;

import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.station.lunch.form.SearchForm;
import com.station.lunch.form.StoreForm;
import com.station.lunch.servise.StoreRegisterService;
import com.station.lunch.session.SessionData;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/register")
@Slf4j
public class StoreRegisterController {
	
	@Autowired
	private HttpSession session;

	@Autowired
	private StoreRegisterService storeRegisterService;

	@Autowired
	private MessageSource messageSource;

	@ModelAttribute("storeForm")
	public StoreForm storeForm() {
		return new StoreForm();
	}

	@ModelAttribute("searchForm")
	public SearchForm searchForm() {
		return new SearchForm();
	}

	@ModelAttribute("sessionData")
	public SessionData getSessionData() {
		return (SessionData) session.getAttribute("sessionData");
	}

	/**
	 * 店舗登録フォームの初期表示を行う
	 * @param storeForm
	 * @param model
	 * @return
	 */
	@GetMapping("/index")
	public String index(@ModelAttribute("storeForm") StoreForm storeForm, Model model) {
		String statusMessage = (String) model.asMap().get("statusMessage");
		model.addAttribute("statusMessage", statusMessage);
		return "store-register";
	}
	
	/**
	 * 店舗編集フォームの表示を行う。
	 * @param storeId
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{storeId}")
	public String edit(@PathVariable("storeId") Long storeId, Model model) {
	    StoreForm storeForm = storeRegisterService.getStore(storeId);
	    model.addAttribute("storeForm", storeForm);
	    return "store-register";
	}

	/**
	 * 店舗の登録申請処理を行う
	 * 
	 * @param storeForm
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("storeForm") StoreForm storeForm,
			BindingResult result, @ModelAttribute("searchForm") SearchForm searchForm,
			RedirectAttributes redirectAttributes, Model model) {

		// 入力項目のバリデーションチェック
		if (result.hasErrors()) {
			return "store-register";
		}
		// データの登録処理を行います
		try {
			// 画像データをデータベースに保存します
			storeRegisterService.saveStore(storeForm);

		} catch (Exception e) {
			// データ登録時にエラーが発生した場合
			String statusMessage = messageSource.getMessage("error.store.register", null, Locale.JAPAN);
			model.addAttribute("statusMessage",statusMessage);
			log.error(statusMessage, e);
			return "store-register";
		}

		// 登録が正常終了した場合
		redirectAttributes.addFlashAttribute("statusMessage", messageSource.getMessage("success.store.register",
				new Object[] { storeForm.getStoreName() }, LocaleContextHolder.getLocale()));
		return "redirect:/register/index";
	}
	
	/**
	 * 店舗の編集申請処理を行う
	 * @param storeForm 店舗フォーム
	 * @param result バリデーション結果
	 * @param model モデル
	 * @return ビュー名
	 */
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("storeForm") StoreForm storeForm,
			BindingResult result,RedirectAttributes redirectAttributes, Model model) {
		
	    // 入力項目のバリデーションチェック
	    if (result.hasErrors()) {
	        // エラーメッセージをセットしてフォームに戻る
	        String statusMessage = result.getAllErrors().stream()
	                .map(objectError -> messageSource.getMessage(objectError, Locale.JAPAN))
	                .collect(Collectors.joining(", "));
			model.addAttribute("statusMessage", statusMessage);
			return "store-register";
	    }

	    try {
	        // データの更新処理を行います
	    	storeRegisterService.updateStore(storeForm);
	        
	    } catch (Exception e) {
	        // エラーログを出力
			String statusMessage = messageSource.getMessage("error.store.update", new Object[] {storeForm.getStoreName()},
					LocaleContextHolder.getLocale());
			model.addAttribute("statusMessage", statusMessage);
			log.error(statusMessage, e);
			return "store-register";
	    }
		// 登録が正常終了した場合
		redirectAttributes.addFlashAttribute("statusMessage", messageSource.getMessage("success.update.store.register",
				new Object[] { storeForm.getStoreName() }, LocaleContextHolder.getLocale()));
		return "redirect:/register/index";	    
	    
	}
}