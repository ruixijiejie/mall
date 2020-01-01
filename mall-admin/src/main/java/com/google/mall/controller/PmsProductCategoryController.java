package com.google.mall.controller;

import com.google.mall.common.CommonPage;
import com.google.mall.common.CommonResult;
import com.google.mall.dto.PmsProductCategoryParam;
import com.google.mall.dto.PmsProductCategoryWithChildrenItem;
import com.google.mall.model.PmsProductCategory;
import com.google.mall.service.PmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类模块Controller
 * Created by sohyun on 2019/12/29 18:09.
 */
@RestController
@RequestMapping(("/productCategory"))
@Api(tags = "PmsProductCategoryController", description = "商品分类管理")

public class PmsProductCategoryController {

    @Autowired
    private PmsProductCategoryService productCategoryService;

    @ApiOperation("添加产品分类")
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('pms:productCategory:create')")
    public CommonResult create(@Validated @RequestBody PmsProductCategoryParam pmsProductCategoryParam) {
        int count = productCategoryService.create(pmsProductCategoryParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改商品分类")
    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('pms:productCategory:update')")
    public CommonResult update(
            @PathVariable Long id,
            @Validated
            @RequestBody PmsProductCategoryParam productCategoryParam) {
        int count = productCategoryService.update(id, productCategoryParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("分页查询商品分类")
    @GetMapping("/list/{parentId}")
    @PreAuthorize("hasAuthority('pms:productCategory:read')")
    public CommonResult<CommonPage> getList(
            @PathVariable Long parentId,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        List<PmsProductCategory> productCategoryList = productCategoryService.getList(parentId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(productCategoryList));
    }

    @ApiOperation("根据id获取商品分类")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('pms:productCategory:read')")
    public CommonResult<PmsProductCategory> getItem(@PathVariable Long id) {
        PmsProductCategory productCategory = productCategoryService.getItem(id);
        return CommonResult.success(productCategory);
    }

    @ApiOperation("删除商品分类")
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('pms:productCategory:delete')")
    public CommonResult delete(@PathVariable Long id) {
        int count = productCategoryService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改导航栏显示状态")
    @PostMapping("/update/navStatus")
    @PreAuthorize("hasAuthority('pms:productCategory:update')")
    public CommonResult updateNavStatus(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("navStatus") Integer navStatus
    ) {
        int count = productCategoryService.updateNavStatus(ids, navStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改显示状态")
    @PostMapping("/update/showStatus")
    @PreAuthorize("hasAuthority('pms:productCategory:update')")
    public CommonResult updateShowStatus(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("showStatus") Integer showStatus
    ) {
        int count = productCategoryService.updateShowStatus(ids, showStatus);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("查询所有一级分类及子分类")
    @GetMapping("/list/withChildren")
    @PreAuthorize("hasAuthority('pms:productCategory:read')")
    public CommonResult<List<PmsProductCategoryWithChildrenItem>> listWithChildren() {
        List<PmsProductCategoryWithChildrenItem> list = productCategoryService.listWithChildren();
        return CommonResult.success(list);
    }
}
