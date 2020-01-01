package com.google.mall.dto;

import com.google.mall.model.PmsProductCategory;
import lombok.Data;

import java.util.List;

/**
 * Created by sohyun on 2019/12/31 22:25.
 */
@Data
public class PmsProductCategoryWithChildrenItem extends PmsProductCategory {
    private List<PmsProductCategory> children;
}
