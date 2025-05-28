package com.Demonew.demo.controller;

import com.Demonew.demo.entity.Order;
import com.Demonew.demo.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    existingOrder.setProduct(updatedOrder.getProduct());
                    existingOrder.setAmount(updatedOrder.getAmount());
                    existingOrder.setUserId(updatedOrder.getUserId());
                    return ResponseEntity.ok(orderRepository.save(existingOrder));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    orderRepository.deleteById(id);
                    return ResponseEntity.ok("Order deleted successfully");
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}




