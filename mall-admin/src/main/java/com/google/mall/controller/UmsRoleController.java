package com.google.mall.controller;

import com.google.mall.common.CommonResult;
import com.google.mall.model.UmsPermission;
import com.google.mall.model.UmsRole;
import com.google.mall.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sohyun on 2019/12/30 19:52.
 */
@RestController
@RequestMapping("/role")
@Api(tags = "UmsRoleController", description = "后台用户角色管理")
public class UmsRoleController {

    @Autowired
    private UmsRoleService roleService;

    @ApiOperation("添加角色")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsRole role) {
        int count = roleService.create(role);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改角色")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody UmsRole role) {
        int count = roleService.update(id, role);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量删除角色")
    @PostMapping("/delete")
    public CommonResult delete(List<Long> ids) {
       int count = roleService.delete(ids);
       if (count > 0) {
           return CommonResult.success(count);
       }
       return CommonResult.failed();
    }

    @ApiOperation("获取所有角色")
    @GetMapping("/list")
    public Object list() {
        List<UmsRole> roleList = roleService.list();
        return CommonResult.success(roleList);
    }

    @ApiOperation("获取相应角色权限")
    @GetMapping("/permission/{roleId}")
    public CommonResult<List<UmsPermission>> getPermissionList(@PathVariable Long roleId) {
        List<UmsPermission> permissionList = roleService.getPermissionList(roleId);
        return CommonResult.success(permissionList);
    }

    @ApiOperation("修改角色权限")
    @PostMapping("/permission/update")
    public CommonResult updatePermission(
            @RequestParam Long roleId,
            @RequestParam("permissionIds") List<Long> permissionIds) {
        int count = roleService.updatePermission(roleId, permissionIds);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
