package com.itheima.service.impl;

import com.itheima.dao.ISysLogDao;
import com.itheima.domain.SysLog;
import com.itheima.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysLogServiceImpl implements ISysLogService {
    @Autowired
    private ISysLogDao dao;

    @Override
    public void save(SysLog sysLog) throws Exception {
        dao.save(sysLog);
    }

    @Override
    public List<SysLog> findAll() throws Exception {
        return dao.findAll();
    }
}
