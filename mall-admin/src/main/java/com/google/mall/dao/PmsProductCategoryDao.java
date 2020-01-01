package com.google.mall.dao;

import com.google.mall.dto.PmsProductCategoryWithChildrenItem;

import java.util.List;

/**
 * Created by sohyun on 2019/12/31 22:30.
 */
public interface PmsProductCategoryDao {
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
