package com.example.bespring.service;
import com.example.bespring.domain.Account;
import com.example.bespring.domain.Order;
import com.example.bespring.domain.OrderDetail;
import com.example.bespring.domain.Product;
import com.example.bespring.dto.OrderDTO;
import com.example.bespring.repository.AcountRepository;
import com.example.bespring.repository.OrderDetailRepository;
import com.example.bespring.repository.OrderRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
     private AcountRepository acountRepository;
    Date today = new Date(System.currentTimeMillis());


    java.sql.Date sqlDate = new java.sql.Date(today.getTime());
    public Order create(OrderDTO orderDTO){
        Order entity = new Order();
        Account account = acountRepository.findByUsername(orderDTO.getUsername());
        entity.setCreateDate(sqlDate);
        entity.setStatus(1);
        entity.setPayment(1);
        entity.setAccount(account);
        entity.setAddress(orderDTO.getAddress());
        Order order = orderRepository.save(entity);
//        System.out.println(order.getId());
        List<Product> listPro = orderDTO.getList();
        for (Product product: listPro){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setPrice(product.getPrice());
            orderDetail.setQuantity(product.getQuantity());
            orderDetailRepository.save(orderDetail);
        }

        return entity;
    }

    public Page<Order> getListByUserName(String username, Pageable pageable){
        return orderRepository.findByAccount_UsernameContainsIgnoreCase(username, pageable);
    }

    public Order confirmOder(int id){
        Order order = orderRepository.findById(id).get();
        order.setStatus(2);
        return orderRepository.save(order);
    }

    public Order cancelOder(int id){
        Order order = orderRepository.findById(id).get();
        order.setStatus(0);
        return orderRepository.save(order);
    }
}
