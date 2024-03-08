package com.craftbay.crafts.controller;

import com.craftbay.crafts.dto.order.OrderResponseDto;
import com.craftbay.crafts.service.AdminOrderService;
import com.craftbay.crafts.util.enums.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    @PostMapping("/{orderId}/update-order")
    public void adminUpdateOrder(@PathVariable("orderId") int orderId,
                                 @RequestParam(value = "status") OrderStatusEnum status) throws Exception {
        adminOrderService.adminUpdateOrder(orderId, status);
    }

    @GetMapping
    public List<OrderResponseDto> getAllOrders() {
        return adminOrderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto getOrderById(@PathVariable("orderId") int orderId) throws Exception {
        return adminOrderService.getOrderById(orderId);
    }

    @GetMapping("/status/{status}")
    public List<OrderResponseDto> getAllOrderByStatus(@PathVariable("status") OrderStatusEnum status) {
        return adminOrderService.getAllOrderByStatus(status);
    }
}
