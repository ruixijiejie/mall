package com.google.mall.dao;

import com.google.mall.dto.PmsProductResult;
import org.apache.ibatis.annotations.Param;

/**
 * Created by sohyun on 2020/1/1 11:37.
 */
public interface PmsProductDao {
    /**
     * 获取商品编辑信息
     */
    PmsProductResult getUpdateInfo(@Param("id") Long id);
}
