package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Orders;
import com.itheima.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private IOrdersService service;

    //提供一个查询方法，查询出订单表中所有信息
    @RequestMapping("/findAll.do")//RequestParam 前端页面需要传递过来的数据，defaultValue 默认数据，name 页面地址栏需要传递的参数 ，required 一定要有参数传递
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "4")Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Orders> all = service.findAll(page,size);
        //PageInfo 就是一个分页查询的实体类，里面封装了分页查询所需要的数据
        PageInfo pageInfo=new PageInfo(all);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("orders-page-list");
        return mv;
    }

    //提供一个根据id查询订单表类所有详细信息的方法,返回订单实体类中的所有信息
    @RequestMapping("/findById.do")//name =id 前端需要传递一个id参数
    public ModelAndView findById(@RequestParam(name = "id",required = true) String ordersId) throws Exception {
          ModelAndView mv=new ModelAndView();
          Orders orders=service.findById(ordersId);
          mv.addObject("orders",orders);
          mv.setViewName("orders-show");
          return mv;
    }
}
