package com.google.mall.service;

import com.google.mall.dto.UmsPermissionNode;
import com.google.mall.mapper.UmsPermissionMapper;
import com.google.mall.model.UmsPermission;
import com.google.mall.model.UmsPermissionExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sohyun on 2019/12/30 19:13.
 */
@Service
public class UmsPermissionService {

    @Autowired
    private UmsPermissionMapper permissionMapper;

    public int create(UmsPermission permission) {
        permission.setStatus(1);
        permission.setCreateTime(new Date());
        permission.setSort(0);
        return permissionMapper.insertSelective(permission);
    }

    public int update(Long id, UmsPermission permission) {
        permission.setId(id);
        return permissionMapper.updateByPrimaryKey(permission);
    }

    public int delete(List<Long> ids) {
        UmsPermissionExample example = new UmsPermissionExample();
        example.createCriteria().andIdIn(ids);
        return permissionMapper.deleteByExample(example);
    }

    public List<UmsPermissionNode> treeList() {
        List<UmsPermission> permissionList = permissionMapper.selectByExample(new UmsPermissionExample());
        List<UmsPermissionNode> result = permissionList.stream()
                .filter(permission -> permission.getPid().equals(0L))
                .map(permission -> covert(permission, permissionList))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * 将权限转换为带有子级的权限对象
     * 当找到子级权限的时候map操作不会再递归调用covert
     * @param permission
     * @param permissionList
     * @return
     */
    private UmsPermissionNode covert(UmsPermission permission, List<UmsPermission> permissionList) {
        UmsPermissionNode node = new UmsPermissionNode();
        BeanUtils.copyProperties(permission, node);
        List<UmsPermissionNode> children = permissionList.stream()
                .filter(subPermission -> subPermission.getPid().equals(permission.getId()))
                .map(subPermission -> covert(subPermission, permissionList))
                .collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    public List<UmsPermission> list() {
        return permissionMapper.selectByExample(new UmsPermissionExample());
    }
}
