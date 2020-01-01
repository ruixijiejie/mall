package com.google.mall.dto;

import com.google.mall.model.UmsPermission;
import lombok.Data;

import java.util.List;

/**
 * Created by sohyun on 2019/12/30 19:31.
 */
@Data
public class UmsPermissionNode extends UmsPermission {
    private List<UmsPermissionNode> children;
}
