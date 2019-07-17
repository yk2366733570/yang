package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.dao.IOrdersDao;
import com.itheima.domain.Orders;
import com.itheima.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class OrdersServiceImpl implements IOrdersService{
    @Autowired
    private IOrdersDao dao;

    public List<Orders> findAll(int page,int size) throws Exception {
        //调用分页插件方法，参数1 是当前页数，参数2是每页显示条数
        PageHelper.startPage(page,size);

        return dao.findAll();
    }

    public Orders findById(String ordersId) throws Exception {
       return dao.findById(ordersId);

    }
}
