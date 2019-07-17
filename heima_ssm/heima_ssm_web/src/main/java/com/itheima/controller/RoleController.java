package com.itheima.controller;

import com.itheima.domain.Permission;
import com.itheima.domain.Role;
import com.itheima.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService service;

    //角色详情代码实现
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id",required = true) String roleId){
        ModelAndView mv = new ModelAndView();
        Role role = service.findById(roleId);
        mv.addObject("role",role);
        mv.setViewName("role-show");
        return mv;
    }

    //给角色添加权限的代码实现,就是把角色id和权限id添加到中间表
    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(name = "roleId",required = true)String roleId,@RequestParam(name = "ids",required = true)String[] ids){
        service.addPermissionToRole(roleId,ids);
        return "redirect:findAll.do";
    }


    //提供一个根据roleId查询出所有可添加权限的方法
    @RequestMapping("/findRoleByIdAndAllPermission.do")
    public ModelAndView findRoleByIdAndAllPermission(@RequestParam(name = "id",required = true) String roleId){
        ModelAndView mv = new ModelAndView();
        //根据角色id查询所有的角色信息
        Role role=service.findById(roleId);

        //根据角色id查询可以添加的权限信息
        List<Permission> permissions= service.findRoleByIdAndAllPermission(roleId);

        mv.addObject("role",role);
        mv.addObject("permissionList",permissions);

        mv.setViewName("role-permission-add");

        return mv;
    }


    //查询所有角色信息的方法
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView();
        List<Role> all = service.findAll();
        mv.addObject("roleList",all);
        mv.setViewName("role-list");
        return mv;
    }

    //添加完成角色信息之后，执行查询所有角色信息的方法
    @RequestMapping("/save.do")
    public String save(Role role) throws Exception {
        service.save(role);
        return "redirect:findAll.do";
    }
}
