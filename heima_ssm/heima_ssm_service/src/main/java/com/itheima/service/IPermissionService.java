package com.itheima.service;

import com.itheima.domain.Permission;

import java.util.List;

public interface IPermissionService {
    void save(Permission permission) throws Exception;

    List<Permission> findAll()throws Exception;
}
