package com.itheima.controller;

import com.itheima.domain.Product;
import com.itheima.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService service;

    /**
     * 查询所有产品
     * @RolesAllowed 配置方法访问所需角色
     * @return
     * @throws Exception
     */
    @RequestMapping("/findAll.do")
    @RolesAllowed("ADMIN")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Product> all = service.findAll();
        mv.addObject("productList",all);
        mv.setViewName("product-list1");
        return mv;
    }
    //添加产品
    @RequestMapping("/save.do")
    public String save(Product product) throws Exception {
        service.saveProduct(product);
        //添加完产品之后跳转到查询页面
        return "redirect:findAll.do";
    }

}
