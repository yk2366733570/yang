package com.itheima.service;

import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

//配置spring-security框架需要的UserService，把账号密码切换成数据库中的
public interface IUserService extends UserDetailsService {


    List<UserInfo> findAll(int page,int size);

    void save(UserInfo userInfo) throws Exception;

    UserInfo findById(String id) throws Exception;

    List<Role> findOtherRole(String userId)throws Exception;

    void addRoleToUser(String userId, String[] rolesId)throws Exception;
}

