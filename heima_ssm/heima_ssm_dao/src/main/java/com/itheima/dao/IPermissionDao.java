package com.itheima.dao;

import com.itheima.domain.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public interface IPermissionDao {
    @Select("select p.* from PERMISSION p,ROLE_PERMISSION rp where p.id=rp.permissionId and rp.roleId=#{id}")
    List<Permission> findById (String id) throws Exception;

    @Insert("insert into permission (permissionName,url) values (#{permissionName},#{url})")
    void save(Permission permission) throws Exception;

    @Select("select * from permission")
   List<Permission> findAll()throws Exception;
}
