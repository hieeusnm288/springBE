package com.example.bespring.controller;

import com.example.bespring.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order_detail")

public class OrderDetailController {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable("id") int id){
        return ResponseEntity.ok().body(orderDetailRepository.findByOrder_Id(id));
    }
}
