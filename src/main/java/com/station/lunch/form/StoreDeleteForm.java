package com.station.lunch.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 店舗情報のフォームクラス
 */
@Data
public class StoreDeleteForm {
	
    /** 店舗ID */
	@NotNull(message = "店舗IDを指定してください")
    private int storeId;

    /** 店舗名 */
    @NotBlank(message = "店舗名を入力してください")
    @Size(max = 50, message = "店舗名は50文字以下で入力してください")
    private String storeName;
}
