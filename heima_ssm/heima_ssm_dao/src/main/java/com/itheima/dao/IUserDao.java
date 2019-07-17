package com.itheima.dao;

import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {
    /**
     * 根据页面传过来的用户名查询用户表数据
     * @param username
     * @return
     * @throws Exception
     */
    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            //查询用户对应的角色信息多对多查询，根据用户的id在中间表中查询到对应的角色信息
            @Result(property = "roles",column = "id",
                    javaType = java.util.List.class,
                    many = @Many(select = "com.itheima.dao.IRoles.findRoleByUserId"))
    })
    UserInfo findByUsername(String username)throws Exception;



    @Select("select * from users")
    List<UserInfo> findAll();

    //添加用户
    @Insert("insert into users(email,username,password,phoneNum,status) values(#{email},#{username},#{password},#{phoneNum},#{status})")
    void save(UserInfo info) throws Exception;

    //查询用户详情，要查询用户信息，角色的所有信息，以及角色对应的所有权限信息
    @Select("select * from users where id=#{id}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            //查询用户对应的角色信息多对多查询，根据用户的id在中间表中查询到对应的角色信息
            @Result(property = "roles",column = "id",
                    javaType = java.util.List.class,
                    many = @Many(select = "com.itheima.dao.IRoles.findRoleByUserId")),


    })
    UserInfo findById(String id) throws Exception;

   // @Select("select * from role where id not in (select roleId from USERS_ROLE where userId=#{userId})")

    @Select("select r.* from role r,USERS_ROLE ur where r.id not in ur.roleId and userId=#{userId}")
    List<Role>findOtherRole(String userId);

    @Insert("insert into USERS_ROLE (userId,roleId) values (#{userId},#{roleId})")
    void addRoleToUser(@Param("userId") String userId,@Param("roleId") String roleId);
}
