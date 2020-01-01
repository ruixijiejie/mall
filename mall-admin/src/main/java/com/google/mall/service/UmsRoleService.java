package com.google.mall.service;

import com.google.mall.dao.UmsRolePermissionRelationDao;
import com.google.mall.mapper.UmsRoleMapper;
import com.google.mall.mapper.UmsRolePermissionRelationMapper;
import com.google.mall.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sohyun on 2019/12/30 19:52.
 */
@Service
public class UmsRoleService {

    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private UmsRolePermissionRelationMapper rolePermissionRelationMapper;

    @Autowired
    private UmsRolePermissionRelationDao rolePermissionRelationDao;

    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setSort(0);
        role.setStatus(1);
        role.setAdminCount(0);
        return roleMapper.insert(role);
    }

    public int update(Long id, UmsRole role) {
        role.setId(id);
        return roleMapper.updateByPrimaryKey(role);
    }

    public int delete(List<Long> ids) {
        UmsRoleExample example = new UmsRoleExample();
        example.createCriteria().andIdIn(ids);
        return roleMapper.deleteByExample(example);
    }

    public List<UmsRole> list() {
        return roleMapper.selectByExample(new UmsRoleExample());
    }

    public List<UmsPermission> getPermissionList(Long roleId) {
        return rolePermissionRelationDao.getPermissionList(roleId);
    }

    public int updatePermission(Long roleId, List<Long> permissionIds) {
        //先删除原有关系
        UmsRolePermissionRelationExample example = new UmsRolePermissionRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        rolePermissionRelationMapper.deleteByExample(example);
        //批量插入新关系
        List<UmsRolePermissionRelation> relationList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            UmsRolePermissionRelation relation = new UmsRolePermissionRelation();
            relation.setRoleId(roleId);
            relation.setPermissionId(permissionId);
            relationList.add(relation);
        }
        return rolePermissionRelationDao.insertList(relationList);
    }
}
