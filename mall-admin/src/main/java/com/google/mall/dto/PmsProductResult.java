package com.google.mall.dto;

import lombok.Data;

/**
 * Created by sohyun on 2020/1/1 11:35.
 */
@Data
public class PmsProductResult extends PmsProductParam {
    //商品所选分类的父id
    private Long cateParentId;
}
