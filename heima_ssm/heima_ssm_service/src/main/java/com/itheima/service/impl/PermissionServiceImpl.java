package com.itheima.service.impl;

import com.itheima.dao.IPermissionDao;
import com.itheima.domain.Permission;
import com.itheima.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private IPermissionDao dao;
    @Override
    public void save(Permission permission) throws Exception {
          dao.save(permission);
    }

    @Override
    public List<Permission> findAll() throws Exception {
        return dao.findAll();
    }
}
