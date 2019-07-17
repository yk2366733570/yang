package com.itheima.service;

import com.itheima.domain.Permission;
import com.itheima.domain.Role;

import java.util.List;

public interface IRoleService {

    List<Role> findAll();

    void save(Role role)throws Exception;

    List<Permission> findRoleByIdAndAllPermission(String roleId);

    Role findById(String roleId);

    void addPermissionToRole(String roleId, String[] ids);
}
