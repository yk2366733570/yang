package com.itheima.service;

import com.itheima.domain.Orders;

import java.util.List;

public interface IOrdersService {
    //分页查询所有订单
    List<Orders> findAll(int page,int size) throws Exception;

    //根据订单id查询订单的详细信息
    Orders findById(String ordersId) throws Exception;
}
