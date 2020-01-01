package com.google.mall.dao;

import com.google.mall.model.UmsPermission;
import com.google.mall.model.UmsRolePermissionRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sohyun on 2019/12/30 20:07.
 */
public interface UmsRolePermissionRelationDao {
    List<UmsPermission> getPermissionList(@Param("roleId") Long roleId);

    int insertList(@Param("list") List<UmsRolePermissionRelation> relationList);
}
