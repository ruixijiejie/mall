package com.google.mall.service;

import com.github.pagehelper.PageHelper;
import com.google.mall.dao.PmsProductCategoryAttributeRelationDao;
import com.google.mall.dao.PmsProductCategoryDao;
import com.google.mall.dto.PmsProductCategoryParam;
import com.google.mall.dto.PmsProductCategoryWithChildrenItem;
import com.google.mall.mapper.PmsProductCategoryAttributeRelationMapper;
import com.google.mall.mapper.PmsProductCategoryMapper;
import com.google.mall.mapper.PmsProductMapper;
import com.google.mall.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sohyun on 2019/12/29 18:10.
 */
@Service
public class PmsProductCategoryService {

    @Autowired
    private PmsProductCategoryMapper productCategoryMapper;

    @Autowired
    private PmsProductMapper productMapper;

    @Autowired
    private PmsProductCategoryAttributeRelationDao productCategoryAttributeRelationDao;

    @Autowired
    private PmsProductCategoryAttributeRelationMapper productCategoryAttributeRelationMapper;

    @Autowired
    private PmsProductCategoryDao productCategoryDao;

    private void setCategoryLevel(PmsProductCategory pmsProductCategory) {
        // 没有父分类为一级分类
        if (pmsProductCategory.getParentId() == 0) {
            pmsProductCategory.setLevel(0);
        } else {
            // 有父分类时选择根据父分类level设置
            PmsProductCategory parentCategory = productCategoryMapper.selectByPrimaryKey(pmsProductCategory.getParentId());
            if (parentCategory != null) {
                pmsProductCategory.setLevel(parentCategory.getLevel() + 1);
            } else {
                pmsProductCategory.setLevel(0);
            }
        }
    }

    public int create(PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setProductCount(0);
        BeanUtils.copyProperties(pmsProductCategoryParam, pmsProductCategory);
        // 没有父分类为以及分类
        setCategoryLevel(pmsProductCategory);
        int count = productCategoryMapper.insertSelective(pmsProductCategory);
        // 创建筛选属性关联
        List<Long> productAttributeIdList = pmsProductCategoryParam.getProductAttributeIdList();
        if (!CollectionUtils.isEmpty(productAttributeIdList)) {
            insertRelationList(pmsProductCategory.getId(), productAttributeIdList);
        }
        return count;
    }

    /**
     * 批量插入商品分类与筛选属性关系表
     *
     * @param productCategoryId
     * @param productAttributeIdList
     */
    private void insertRelationList(Long productCategoryId, List<Long> productAttributeIdList) {
        List<PmsProductCategoryAttributeRelation> relationList = new ArrayList<>();
        for (Long productAttrId : productAttributeIdList) {
            PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
            relation.setProductAttributeId(productAttrId);
            relation.setProductCategoryId(productCategoryId);
            relationList.add(relation);
        }
        productCategoryAttributeRelationDao.insertList(relationList);
    }

    public List<PmsProductCategory> getList(Long parentId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return productCategoryMapper.selectByExample(example);
    }

    public int delete(Long id) {
        return productCategoryMapper.deleteByPrimaryKey(id);
    }

    public int updateNavStatus(List<Long> ids, Integer navStatus) {
        PmsProductCategory productCategory = new PmsProductCategory();
        productCategory.setNavStatus(navStatus);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.createCriteria().andIdIn(ids);
        return productCategoryMapper.updateByExampleSelective(productCategory, example);
    }

    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        PmsProductCategory productCategory = new PmsProductCategory();
        productCategory.setShowStatus(showStatus);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.createCriteria().andIdIn(ids);
        return productCategoryMapper.updateByExampleSelective(productCategory, example);
    }

    public List<PmsProductCategoryWithChildrenItem> listWithChildren() {
        return productCategoryDao.listWithChildren();
    }

    public PmsProductCategory getItem(Long id) {
        return productCategoryMapper.selectByPrimaryKey(id);
    }

    public int update(Long id, PmsProductCategoryParam productCategoryParam) {
        PmsProductCategory productCategory = new PmsProductCategory();
        productCategory.setId(id);
        BeanUtils.copyProperties(productCategoryParam, productCategory);
        setCategoryLevel(productCategory);
        //更新商品分类时要更新商品中的名称
        PmsProduct product = new PmsProduct();
        product.setProductCategoryName(productCategory.getName());
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andProductCategoryIdEqualTo(id);
        productMapper.updateByExampleSelective(product, example);
        //同时更新筛选属性的信息
        if (!CollectionUtils.isEmpty(productCategoryParam.getProductAttributeIdList())) {
            PmsProductCategoryAttributeRelationExample relationExample = new PmsProductCategoryAttributeRelationExample();
            relationExample.createCriteria().andProductCategoryIdEqualTo(id);
            productCategoryAttributeRelationMapper.deleteByExample(relationExample);
            insertRelationList(id, productCategoryParam.getProductAttributeIdList());
        } else {
            PmsProductCategoryAttributeRelationExample relationExample = new PmsProductCategoryAttributeRelationExample();
            relationExample.createCriteria().andProductCategoryIdEqualTo(id);
            productCategoryAttributeRelationMapper.deleteByExample(relationExample);
        }
        return productCategoryMapper.updateByPrimaryKeySelective(productCategory);
    }
}
