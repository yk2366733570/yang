package com.itheima.dao;

import com.itheima.domain.Traveller;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITravellerDao {
    //多对多查询，查询出旅客表和订单表在中间表中关联的数据，根据订单表id查询旅客表id，再把旅客表id当条件查询

   // @Select("select * from traveller where id in (select travellerId from order_traveller where orderId=#{ordersId})")
    @Select("select t.* from traveller t,order_traveller ot where t.id=ot.travellerId and ot.orderId=#{ordersId}")
    public List<Traveller> findByOrdersId(String ordersId) throws Exception;
}
