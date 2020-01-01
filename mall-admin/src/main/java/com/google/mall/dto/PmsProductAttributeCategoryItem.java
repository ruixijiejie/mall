package com.google.mall.dto;

import com.google.mall.model.PmsProductAttribute;
import com.google.mall.model.PmsProductAttributeCategory;
import lombok.Data;

import java.util.List;

/**
 * Created by sohyun on 2019/12/31 23:22.
 */
@Data
public class PmsProductAttributeCategoryItem extends PmsProductAttributeCategory {
    List<PmsProductAttribute> productAttributeList;
}
