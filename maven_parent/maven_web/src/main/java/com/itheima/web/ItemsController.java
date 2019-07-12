package com.itheima.web;

import com.itheima.domain.Items;
import com.itheima.service.IItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemsController {
    @Autowired
    private IItemsService service;

    @RequestMapping("/find")
    public String findItems(Model model){
        Items items = service.findById(1);
        model.addAttribute("item",items);
        return "itemDetail";
    }

}
