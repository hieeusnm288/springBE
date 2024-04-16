package com.example.bespring.controller;

import com.example.bespring.domain.Order;
import com.example.bespring.dto.OrderDTO;
import com.example.bespring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping()
    public ResponseEntity<?> crate(@RequestBody OrderDTO order){

        orderService.create(order);
        return ResponseEntity.ok().body("Tao hoa don thanh cong");
    }
    @GetMapping("/search")
    public ResponseEntity<?> getListOder(@RequestParam(required = false) String name ,
                                            @PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.ASC)
                                            Pageable pageable)
    {
        return new ResponseEntity<>(orderService.getListByUserName(name, pageable), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> confirmOrder(@PathVariable("id") int id){
        orderService.confirmOder(id);
        return ResponseEntity.ok().body("Xác nhận đơn hàng thành công");
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable("id") int id){
        orderService.cancelOder(id);
        return ResponseEntity.ok().body("Hủy đơn hàng thành công");
    }
}
