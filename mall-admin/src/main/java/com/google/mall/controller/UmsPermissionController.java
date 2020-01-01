package com.google.mall.controller;

import com.google.mall.common.CommonResult;
import com.google.mall.dto.UmsPermissionNode;
import com.google.mall.model.UmsPermission;
import com.google.mall.service.UmsPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台用户权限管理
 * Created by sohyun on 2019/12/30 19:12.
 */

@RestController
@RequestMapping("/permission")
@Api(tags = "UmsPermissionController", description = "后台用户权限管理")
public class UmsPermissionController {

    @Autowired
    private UmsPermissionService permissionService;

    @ApiOperation("添加权限")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsPermission permission) {
        int count = permissionService.create(permission);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改权限")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody UmsPermission permission) {
       int count = permissionService.update(id, permission);
       if (count > 0) {
           return CommonResult.success(count);
       }
       return CommonResult.failed();
    }

    @ApiOperation("根据id批量删除权限")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam("ids")List<Long> ids) {
        int count = permissionService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("以层级结构返回所有权限")
    @GetMapping("/treeList")
    public CommonResult<List<UmsPermissionNode>> treeList() {
        List<UmsPermissionNode> permissionNodeList = permissionService.treeList();
        return CommonResult.success(permissionNodeList);
    }

    @ApiOperation("获取所有权限列表")
    @GetMapping("/list")
    public CommonResult<List<UmsPermission>> list() {
        List<UmsPermission> permissionList = permissionService.list();
        return CommonResult.success(permissionList);
    }
}
