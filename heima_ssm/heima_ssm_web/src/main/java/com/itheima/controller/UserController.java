package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import com.itheima.service.IUserService;
import com.itheima.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService service;


    //给用户添加角色代码实现
    @RequestMapping("/addRoleToUser.do")
    //用户添加角色信息，就是把添加角色id和用户id添加到角色和用户的中间表进行关联
    public String addRoleToUser(@RequestParam(name = "userId",required = true)String userId,@RequestParam(name = "ids",required = true)String[] rolesId) throws Exception {
        service.addRoleToUser(userId,rolesId);
        return "redirect:findAll.do";
    }



    //在用户信息页面点击添加角色按钮的代码实现
    //要查询根据用户id查询用户信息，同时也需要查询可以添加的角色信息
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id",required = true) String userId) throws Exception {
         //根据用户id查询用户的信息
        UserInfo userInfo = service.findById(userId);
        //查询能添加的角色信息
        List<Role> roles =service.findOtherRole(userId);

        ModelAndView mv = new ModelAndView();

        mv.addObject("user",userInfo);
        mv.addObject("roleList",roles);
        mv.setViewName("user-role-add");
        return mv;
    }


    @RequestMapping("/findById.do")
   public ModelAndView findById(String id) throws Exception {
       ModelAndView mv = new ModelAndView();
       UserInfo userInfo=service.findById(id);
       mv.addObject("user",userInfo);
       mv.setViewName("user-show1");
       return mv;
   }


    @RequestMapping("findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "4")Integer size){
        ModelAndView mv = new ModelAndView();
       List<UserInfo> list =service.findAll(page,size);
       //PageInfo 就是一个分页查询的实体类，里面封装了分页查询所需要的数据
       PageInfo pageInfo=new PageInfo(list);
       mv.addObject("pageInfo",pageInfo);
       mv.addObject("userList",list);
       mv.setViewName("user-list");

        return mv;
    }
    //添加用户的方法
    @RequestMapping("/save.do")
    public String save(UserInfo userInfo) throws Exception {
       service.save(userInfo);

       return "redirect:findAll.do";
    }

}
