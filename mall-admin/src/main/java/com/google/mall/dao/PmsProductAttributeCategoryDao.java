package com.google.mall.dao;

import com.google.mall.dto.PmsProductAttributeCategoryItem;

import java.util.List;

/**
 * Created by sohyun on 2019/12/31 23:24.
 */
public interface PmsProductAttributeCategoryDao {
    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
