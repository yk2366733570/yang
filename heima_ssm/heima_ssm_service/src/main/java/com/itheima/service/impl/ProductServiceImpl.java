package com.itheima.service.impl;

import com.itheima.dao.IProductDao;
import com.itheima.domain.Product;
import com.itheima.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ProductServiceImpl implements IProductService {
    @Autowired
    IProductDao dao;

    public List<Product> findAll() throws Exception {
        return dao.findAll();
    }

    public void saveProduct(Product product) throws Exception {
        dao.saveProduct(product);
    }

}
