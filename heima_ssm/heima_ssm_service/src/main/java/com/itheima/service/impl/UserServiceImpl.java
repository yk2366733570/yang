package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.dao.IUserDao;
import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import com.itheima.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.font.ShapeGraphicAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService{
    @Autowired
    private IUserDao dao;

    @Autowired//注入一个spring-security.xml配置的加密类，对users表中的密码进行加密
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo=null;
        try {
            userInfo= dao.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //创建UserDetails的实现类security提供的User对象，传入dao层查询到的账号密码和账号对应角色信息
       /* User user=new User(userInfo.getUsername(),
                "{noop}"+userInfo.getPassword(),
                //判断账号是否可用，数据库中Status=0代表不可用，1代表可用
                userInfo.getStatus()==0?false:true,
                /**security提供的User对象的参数1是账户是否过期
                 * 参数2是账户是否锁定
                 * 参数3是账户是否认证过期
                true,true,true,
                //对数据库角色进行security授权之后再返回
                getAuthority(userInfo.getRoles()));
        return user;*/
        User user = new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.getStatus() == 0 ? false : true, true, true, true, getAuthority(userInfo.getRoles()));
        return user;

    }
    //定义一个方法把从数据库获取出来的角色信息进行授权，满足security配置文件中的访问权限需求
    public List<SimpleGrantedAuthority>getAuthority(List<Role> roles){

        List<SimpleGrantedAuthority> list=new ArrayList<SimpleGrantedAuthority>();
        //遍历数据库的角色信息集合,给数据库角色信息赋予security中需要的角色权限ROLE_User或者ROLE_ADMIN
        for (Role role : roles) {

            list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }

        return list;
    }

    @Override
    public List<UserInfo> findAll(int page ,int size) {
        //调用分页插件方法，参数1 是当前页数，参数2是每页显示条数
        PageHelper.startPage(page,size);
        return dao.findAll();
    }

    @Override
    public void save(UserInfo userInfo) throws Exception {
        //对密码进行加密处理
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        dao.save(userInfo);
    }

    @Override
    public UserInfo findById(String id) throws Exception {
        return dao.findById(id);
    }

    @Override
    public List<Role> findOtherRole(String userId) throws Exception {
        return dao.findOtherRole(userId);
    }

    @Override
    public void addRoleToUser(String userId, String[] rolesId) throws Exception {
        for (String roleId : rolesId) {
            dao.addRoleToUser(userId,roleId);
        }
    }
}
