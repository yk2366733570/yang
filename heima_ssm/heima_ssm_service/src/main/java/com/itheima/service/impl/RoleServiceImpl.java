package com.itheima.service.impl;

import com.itheima.dao.IRoles;
import com.itheima.domain.Permission;
import com.itheima.domain.Role;
import com.itheima.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoles roles;

    @Override
    public List<Role> findAll() {
        return roles.findAll();
    }

    @Override
    public void save(Role role) throws Exception {
        roles.save(role);
    }

    @Override
    public List<Permission> findRoleByIdAndAllPermission(String roleId) {
        return roles.findRoleByIdAndAllPermission(roleId);
    }

    @Override
    public Role findById(String roleId) {
        return roles.findById(roleId);
    }

    @Override
    public void addPermissionToRole(String roleId, String[] ids) {
        for (String permissionId : ids) {
            roles.addPermissionToRole(roleId,permissionId);
        }
    }
}
