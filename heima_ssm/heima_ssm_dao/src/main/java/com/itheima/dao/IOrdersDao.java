package com.itheima.dao;

import com.itheima.domain.Member;
import com.itheima.domain.Orders;
import com.itheima.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
//订单查询，在查询订单的同时能查询出订单对应的产品信息
public interface IOrdersDao {

    //查询订单表的所有信息，并根据订单表的产品id查询产品的信息
    @Select("select * from orders")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "orderNum",column = "orderNum"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "orderStatus",column = "orderStatus"),
            @Result(property = "peopleCount",column = "peopleCount"),
            @Result(property = "payType",column = "payType"),
            @Result(property = "orderDesc",column = "orderDesc"),
            /**
             * 级联查询，调用IProductDao的findById方法，返回一个javaType = Product.class 类型数据
             * product 是订单实体类的数据，productId 是订单表中和产品id关联的数据，提供给findById方法查询产品数据
             */
            @Result(property = "product",column = "productId",
                    javaType = Product.class,
                    one = @One(select = "com.itheima.dao.IProductDao.findById"))
    })
    List<Orders> findAll() throws Exception;

    /**
     * 根据订单id查询订单表中的详细信息，不仅要查询订单信息还要查询产品，旅客和会员的信息
     * @param ordersId
     * @return
     * @throws Exception
     */
    @Select("select * from orders where id=#{ordersId}")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "orderNum",column = "orderNum"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "orderStatus",column = "orderStatus"),
            @Result(property = "peopleCount",column = "peopleCount"),
            @Result(property = "payType",column = "payType"),
            @Result(property = "orderDesc",column = "orderDesc"),
            //查询产品信息，调用产品dao层的根据id查询方法，一对一查询
            @Result(property = "product",column = "productId",
                    javaType = Product.class,
                    one = @One(select = "com.itheima.dao.IProductDao.findById")),
            //查询旅客信息，调用会员dao层的根据id查询的方法，一对一查询
            @Result(property = "member",column = "memberId",
                    javaType = Member.class,
                    one = @One(select = "com.itheima.dao.IMemberDao.findById")),
            //查询旅客信息，返回的是一个集合,多对多查询，借助中间表，订单的id在中间表对应旅客的id
            @Result(property = "travellers",column = "id",
                    javaType =java.util.List.class,
                    many = @Many(select = "com.itheima.dao.ITravellerDao.findByOrdersId"))
    })

    Orders findById(String ordersId) throws Exception;


















}
