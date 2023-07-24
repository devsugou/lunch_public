package com.station.lunch.form;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.station.lunch.dto.MenuDetailDto;
import com.station.lunch.validator.ValidImage;

import lombok.Data;

/**
 * 店舗情報のフォームクラス
 */
@Data
@Valid
public class StoreForm {
	
    /** 店舗ID */
    private int storeId;

    /** 店舗名 */
    @NotBlank(message = "{validation.store.name.blank}")
    @Size(max = 50, message = "{validation.store.name.size}")
    private String storeName;

    /** 店舗の電話番号 */
    @Pattern(regexp = "^$|0\\d{1,4}-\\d{1,4}-\\d{4}$", message = "{validation.store.phone.format}")
    private String storePhoneNumber;

    /** 店舗の住所 */
    @NotBlank(message = "{validation.store.address.blank}")
    @Size(max = 50, message = "{validation.store.address.size}")
    private String storeAddress;

    /** 店舗のURL */
    @Pattern(regexp = "^$|^(http|https)://[^\\s\"]+$", message = "{validation.store.url.format}")
    @Size(max = 50, message = "{validation.store.url.size}")
    private String storeUrl;

    /** 店舗の営業日 */
    @Pattern(regexp = "^$|^(日|月|火|水|木|金|土)(,\\s*(日|月|火|水|木|金|土))*$", message = "{validation.store.business.day.format}")
    private String storeBusinessDay;
    private List<String> storeBusinessDaysList;
    
    /** 店舗の営業開始時間 */
    @Pattern(regexp = "^$|^([01]\\d|2[0-3]):([0-5]\\d)$", message = "{validation.store.business.hours.start.format}")
    private String storeBusinessStartHours;

    /** 店舗の営業終了時間 */
    @Pattern(regexp = "^$|^([01]\\d|2[0-3]):([0-5]\\d)$", message = "{validation.store.business.hours.end.format}")
    private String storeBusinessEndHours;

    /** 店舗の定休日 */
    @Pattern(regexp = "^$|^(日|月|火|水|木|金|土)+$", message = "{validation.store.closing.day.format}")
    private String storeRegularClosingDay;

    /** 店舗画像 */
    @ValidImage
    private MultipartFile storeImageData;

    /** 店舗の説明 */
    @Size(max = 50, message = "{validation.store.description.size}")
    private String storeDescription;

    /** ジャンルID */
    @NotNull(message = "{validation.store.genreId.null}")
    @Min(value = 1, message = "{validation.store.genreId.null}")
    private Integer genreId;

    /** 距離ID */
    @NotNull(message = "{validation.store.distanceId.null}")
    @Min(value = 1, message = "{validation.store.distanceId.null}")
    private Integer distanceId;

    /** 評価スターID */
    @NotNull(message = "{validation.store.evaluationStarId.null}")
    @Min(value = 1, message = "{validation.store.evaluationStarId.null}")
    private Integer evaluationStarId;

    /** 評価スター数 */
    private Double evaluationStarCount;

    /** メニュー情報リスト */
    private List<MenuDetailDto> menuDetailInfoList;
    
}
