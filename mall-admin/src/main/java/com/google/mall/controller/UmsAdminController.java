package com.google.mall.controller;

import com.google.mall.common.CommonPage;
import com.google.mall.common.CommonResult;
import com.google.mall.dto.UmsAdminLoginParam;
import com.google.mall.dto.UmsAdminParam;
import com.google.mall.dto.UpdateAdminPasswordParam;
import com.google.mall.model.UmsAdmin;
import com.google.mall.model.UmsPermission;
import com.google.mall.model.UmsRole;
import com.google.mall.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sohyun on 2019/12/29 20:58.
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "UmsAdminController", description = "后台用户管理")
public class UmsAdminController {

    @Autowired
    private UmsAdminService adminService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "登录以后返回token")
    @PostMapping("/login")
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "刷新token")
    @GetMapping("/refreshToken")
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/info")
    public CommonResult getAdminInfo(Principal principal) {
        String username = principal.getName();
        UmsAdmin umsAdmin = adminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        data.put("roles", new String[]{"TEST"});
        data.put("icon", umsAdmin.getIcon());
        return CommonResult.success(data);
    }

    @ApiOperation(value = "登出功能")
    @PostMapping("/logout")
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<UmsAdmin>> list(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UmsAdmin> adminList = adminService.list(name, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping("/{id}")
    public CommonResult<UmsAdmin> getItem(@PathVariable Long id) {
        UmsAdmin admin = adminService.getItem(id);
        return CommonResult.success(admin);
    }

    @ApiOperation("修改指定用户信息")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody UmsAdmin admin) {
        int count = adminService.update(id, admin);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改指定用户密码")
    @PostMapping("/updatePassword")
    public CommonResult updatePassword(@RequestBody UpdateAdminPasswordParam updatePasswordParam) {
        int status = adminService.updatePassword(updatePasswordParam);
        if (status > 0) {
            return CommonResult.success(status);
        } else if (status == -1) {
            return CommonResult.failed("提交参数不合法");
        } else if (status == -2) {
            return CommonResult.failed("找不到该用户");
        } else if (status == -3) {
            return CommonResult.failed("旧密码错误");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除指定用户信息")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int count = adminService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("给用户分配角色")
    @PostMapping("/role/update")
    public CommonResult updateRole(
            @RequestParam("adminId") Long adminId,
            @RequestParam("roleIds") List<Long> roleIds) {
        int count = adminService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping("/role/{adminId}")
    public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
        List<UmsRole> roleList = adminService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("给用户分配+-权限")
    @PostMapping("/permission/update")
    public CommonResult updatePermission(
            @RequestParam Long adminId,
            @RequestParam("permissionIds") List<Long> permissionIds) {
        int count = adminService.updatePermission(adminId, permissionIds);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取用户所有权限（包括+-权限）")
    @GetMapping("/permission/{adminId}")
    public CommonResult<List<UmsPermission>> getPermissionList(@PathVariable Long adminId) {
        List<UmsPermission> permissionList = adminService.getPermissionList(adminId);
        return CommonResult.success(permissionList);
    }
}
