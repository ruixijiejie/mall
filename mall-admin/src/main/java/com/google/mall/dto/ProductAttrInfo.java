package com.google.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品分类对应属性信息
 * Created by sohyun on 2020/1/1 00:03.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttrInfo {
    private Long attributeId;
    private Long attributeCategoryId;
}
