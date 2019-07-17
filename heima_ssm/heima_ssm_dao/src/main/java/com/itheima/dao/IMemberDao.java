package com.itheima.dao;

import com.itheima.domain.Member;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberDao {
    //提供一个方法查询会员信息
    @Select("select * from member where id=#{id}")
    public Member findById(String id) throws Exception;
}
