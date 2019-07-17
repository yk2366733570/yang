package com.itheima.dao;

import com.itheima.domain.Permission;
import com.itheima.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 提供一个方法，根据用户的id查询用户所对应的角色
 */
@Repository
public interface IRoles {
    //根据用户id在中间表查询角色对应id,再作为条件查询所有角色信息
    //@Select("select * from role where id in (select roleId from USERS_ROLE where userId=#{userId})")
    @Select("select r.* from role r,USERS_ROLE ur where r.id=ur.roleId and ur.userId=#{userId}")
    //根据id查询所有角色信息的同时，也查询出角色信息对应的权限信息
    @Results({
            @Result(id = true,property = "id",column = "id"),
            //角色表和权限表的级联查询，借助中间表进查询
            @Result(property = "permissions",column = "id",
                    javaType =java.util.List.class,
                    many =@Many(select ="com.itheima.dao.IPermissionDao.findById"))
    })
    List<Role>findRoleByUserId(String userId) throws Exception;

    @Select("select * from role")
    List<Role> findAll();

    @Insert("insert into role (roleName,roleDesc) values (#{roleName},#{roleDesc})")
    void save(Role role)throws Exception;

    //根据角色id查询未添加的权限
    @Select("select p.* from PERMISSION p,ROLE_PERMISSION rp where p.id not in rp.permissionId and rp.roleId=#{roleId}")
    List<Permission> findRoleByIdAndAllPermission(String roleId);

    @Select("select * from role where id=#{roleId}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            //角色表和权限表的级联查询，借助中间表进查询
            @Result(property = "permissions",column = "id",
                    javaType =java.util.List.class,
                    many =@Many(select ="com.itheima.dao.IPermissionDao.findById"))
    })
    Role findById(String roleId);

    @Insert("insert into ROLE_PERMISSION (roleId,permissionId) values (#{roleId},#{permissionId})")
    void addPermissionToRole(@Param("roleId") String roleId, @Param("permissionId") String permissionId);
}
