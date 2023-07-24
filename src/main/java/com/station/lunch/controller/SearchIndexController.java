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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.station.lunch.form.SearchForm;
import com.station.lunch.form.StoreDeleteForm;
import com.station.lunch.form.StoreForm;
import com.station.lunch.servise.SearchIndexService;
import com.station.lunch.session.SessionData;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/store")
@Slf4j
public class SearchIndexController {
	
	private static final String ACTION_STORE_SEARCH = "storeSearch";
	private static final String ACTION_REGISTER_INDEX = "registerIndex";

    @Autowired
    private HttpSession session;

	@Autowired
	private SearchIndexService searchIndexService;

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
	 * ボタン毎にリダイレクトするメソッドを制御する
	 * @param action
	 * @param searchForm
	 * @param storeForm
	 * @param redirectAttrs
	 * @return
	 */
	@GetMapping("/handle")
	public String handle(@RequestParam("action") String action, 
	                     @ModelAttribute("searchForm") SearchForm searchForm, 
	                     @ModelAttribute("storeForm") StoreForm storeForm,
	                     RedirectAttributes redirectAttrs) {
	    //検索ボタン押下
	    if (ACTION_STORE_SEARCH.equals(action)) {
	        redirectAttrs.addFlashAttribute("searchForm", searchForm);
	        return "redirect:/store/search";
	        
	    //登録ボタン押下
	    } else if (ACTION_REGISTER_INDEX.equals(action)) {
	        redirectAttrs.addFlashAttribute("storeForm", storeForm);
	        return "redirect:/register/index";
	    }

	    // Default case
	    return "search-index";
	}

	/**
	 * 店舗検索・一覧画面の初期表示と検索処理を行う
	 *
	 * @param searchForm 検索フォーム
	 * @param model モデル
	 * @return ビュー名
	 */
	@GetMapping({"/index", "/search"})
	public String indexOrSearch(@ModelAttribute("searchForm") SearchForm searchForm, Model model) {
	    if ("/index".equals(model.asMap().get("requestUri"))) {
	        String statusMessage = (String) model.asMap().get("statusMessage");
	        model.addAttribute("statusMessage", statusMessage);
	    }

	    try {
	        // 店舗情報一覧を取得
	        searchForm = searchIndexService.getStores(searchForm);
	        log.info(messageSource.getMessage("success.store.search", null, Locale.JAPAN));
	        return "search-index";
	    } catch (Exception e) {
	        log.error(messageSource.getMessage("error.store.search", null, Locale.JAPAN), e);
	        return "search-index";
	    }
	}


	/**
	 * 店舗の削除処理を行う
	 * 
	 * @param storeId
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/delete")
	public String storeDelete(@Valid @ModelAttribute("storeDeleteForm") StoreDeleteForm storeDeleteForm,
			BindingResult result, RedirectAttributes redirectAttributes, Model model) {

		// バリデーションエラーがある場合、フォームを再表示します
		if (result.hasErrors()) {
		    String statusMessage = result.getAllErrors().stream()
		        .map(objectError -> messageSource.getMessage(objectError, Locale.JAPAN))
		        .collect(Collectors.joining(", "));
		    model.addAttribute("statusMessage", statusMessage);
		    return "redirect:/store/index";
		}
		int storeId = storeDeleteForm.getStoreId();
		String storeName = storeDeleteForm.getStoreName();

		// データの削除処理を行います
		try {
			// 指定されたIDの店舗情報をデータベースから削除します
			searchIndexService.deleteStore(storeId);
		} catch (Exception e) {
			// 削除処理中にエラーが発生した場合、適切なエラーハンドリングを行います
			String statusMessage = messageSource.getMessage("error.store.delete", new Object[] { storeName },
					LocaleContextHolder.getLocale());
			model.addAttribute("statusMessage", statusMessage);
			log.error(statusMessage,e);
			return "search-index";
		}

		// Flash Attributeの設定
		redirectAttributes.addFlashAttribute("statusMessage", messageSource.getMessage("success.store.delete",
				new Object[] { storeName }, LocaleContextHolder.getLocale()));

		// 削除が完了したら、適切なビューを返します
		return "redirect:/store/index";
	}
	
}