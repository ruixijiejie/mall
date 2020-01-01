package com.google.mall.service;

import com.github.pagehelper.PageHelper;
import com.google.mall.dao.PmsProductAttributeCategoryDao;
import com.google.mall.dto.PmsProductAttributeCategoryItem;
import com.google.mall.mapper.PmsProductAttributeCategoryMapper;
import com.google.mall.model.PmsProductAttributeCategory;
import com.google.mall.model.PmsProductAttributeCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sohyun on 2019/12/31 22:55.
 */
@Service
public class PmsProductAttributeCategoryService {

    @Autowired
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;

    @Autowired
    private PmsProductAttributeCategoryDao productAttributeCategoryDao;

    public int create(String name) {
        PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
        productAttributeCategory.setName(name);
        return productAttributeCategoryMapper.insertSelective(productAttributeCategory);
    }

    public int update(Long id, String name) {
        PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
        productAttributeCategory.setName(name);
        productAttributeCategory.setId(id);
        return productAttributeCategoryMapper.updateByPrimaryKeySelective(productAttributeCategory);
    }

    public int delete(Long id) {
        return productAttributeCategoryMapper.deleteByPrimaryKey(id);
    }

    public PmsProductAttributeCategory getItem(Long id) {
        return productAttributeCategoryMapper.selectByPrimaryKey(id);
    }

    public List<PmsProductAttributeCategory> getList(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return productAttributeCategoryMapper.selectByExample(new PmsProductAttributeCategoryExample());
    }

    public List<PmsProductAttributeCategoryItem> getListWithAttr() {
        return productAttributeCategoryDao.getListWithAttr();
    }
}
