package com.google.mall.controller;

import com.google.mall.common.CommonPage;
import com.google.mall.common.CommonResult;
import com.google.mall.dto.PmsProductParam;
import com.google.mall.dto.PmsProductQueryParam;
import com.google.mall.dto.PmsProductResult;
import com.google.mall.model.PmsProduct;
import com.google.mall.service.PmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sohyun on 2020/1/1 10:12.
 */
@RestController
@RequestMapping("/product")
@Api(tags = "PmsProductController", description = "商品管理")
public class PmsProductController {

    @Autowired
    private PmsProductService productService;

    @ApiOperation("创建商品")
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('pms:product:create')")
    public CommonResult create(@RequestBody PmsProductParam productParam) {
        int count = productService.create(productParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据商品id获取商品编辑信息")
    @GetMapping("/updateInfo/{id}")
    @PreAuthorize("hasAuthority('pms:product:read')")
    public CommonResult<PmsProductResult> getUpdateInfo(@PathVariable Long id) {
        PmsProductResult productResult = productService.getUpdateInfo(id);
        return CommonResult.success(productResult);
    }

    @ApiOperation("更新商品")
    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('pms:product:update')")
    public CommonResult update(
            @PathVariable Long id,
            @RequestBody PmsProductParam productParam
    ) {
        int count = productService.update(id, productParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("查询商品")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:product:read')")
    public CommonResult<CommonPage<PmsProduct>> getList(
            PmsProductQueryParam productQueryParam,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        List<PmsProduct> productList = productService.list(productQueryParam, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(productList));
    }

    @ApiOperation("根据商品名称或货号模糊查询")
    @GetMapping("/simpleList")
    public CommonResult<List<PmsProduct>> getList(String keyword) {
        List<PmsProduct> productList = productService.list(keyword);
        return CommonResult.success(productList);
    }

    @ApiOperation("批量修改审核状态")
    @PostMapping("/update/verifyStatus")
    @PreAuthorize("hasAuthority('pms:product:update')")
    public CommonResult updateVerifyStatus(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("verifyStatus") Integer verifyStatus,
            @RequestParam("detail") String detail) {
        int count = productService.updateVerifyStatus(ids, verifyStatus, detail);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量上下架")
    @PostMapping("/update/publishStatus")
    @PreAuthorize("hasAuthority('pms:product:update')")
    public CommonResult updatePublishStatus(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("publishStatus") Integer publishStatus) {
        int count = productService.updatePublishStatus(ids, publishStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量推荐商品")
    @PostMapping("/update/recommendStatus")
    @PreAuthorize("hasAuthority('pms:product:update')")
    public CommonResult updateRecommendStatus(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("recommendStatus") Integer recommendStatus) {
        int count = productService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量设为新品")
    @PostMapping("/update/newStatus")
    @PreAuthorize("hasAuthority('pms:product:update')")
    public CommonResult updateNewStatus(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("newStatus") Integer newStatus) {
        int count = productService.updateNewStatus(ids, newStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量修改删除状态")
    @PostMapping("/update/deleteStatus")
    @PreAuthorize("hasAuthority('pms:product:delete')")
    public CommonResult updateDeleteStatus(
            @RequestParam("ids") List<Long> ids,
            @RequestParam("deleteStatus") Integer deleteStatus) {
        int count = productService.updateDeleteStatus(ids, deleteStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
}
