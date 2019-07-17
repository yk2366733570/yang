package com.itheima.dao;

import com.itheima.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductDao {
    /**
     * 查询产品
     * @return
     * @throws Exception
     */
    @Select("select * from product")
    List<Product> findAll() throws Exception;

    /**
     * 添加产品
     * @param product
     * @throws Exception
     */
    @Insert("insert into product (productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    void saveProduct(Product product)throws Exception;

    /**
     * 查询订单是需要根据订单信息显示一个产品信息，多对一查询，需要提供一个根据id查询的方法
     * @param id
     * @return
     * @throws Exception
     */
    @Select("select * from product where id=#{id}")
    Product findById(String id) throws Exception;
}
