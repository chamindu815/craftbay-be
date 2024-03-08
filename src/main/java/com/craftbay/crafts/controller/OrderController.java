package com.craftbay.crafts.controller;
import com.craftbay.crafts.dto.order.OrderResponseDto;
import com.craftbay.crafts.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}/cart/{cartId}/place-order")
    public String placeOrder(@PathVariable("userId") int userId, @PathVariable("cartId") int cartId) throws Exception {
        return orderService.placeOrder(userId,cartId);
    }

    @PostMapping("/{userId}/order/{orderId}/cancel-order")
    public String cancelOrder(@PathVariable("userId") int userId, @PathVariable("orderId") int orderId) throws Exception {
        return orderService.cancelOrder(userId,orderId);
    }

    @GetMapping("/{userId}/my-orders")
    public List<OrderResponseDto> viewMyOrders(@PathVariable("userId") int userId) throws Exception {
        return orderService.viewMyOrders(userId);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public OrderResponseDto viewOrderById(@PathVariable("userId") int userId, @PathVariable("orderId") int orderId) throws Exception {
        return orderService.viewOrderById(userId, orderId);
    }
}
