package com.itheima.controller;

import com.itheima.domain.Permission;
import com.itheima.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService service;

    //查询所有权限的方法
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Permission> all = service.findAll();
        mv.addObject("permissionList",all);
        mv.setViewName("permission-list");
        return mv;
    }


    //添加权限的方法，添加完成之后执行查询所有权限的方法
    @RequestMapping("/save.do")
    public String save(Permission permission) throws Exception {
        service.save(permission);
        return "redirect:findAll.do";
    }


}
