package com.demonew.user_service.feign;

import com.demonew.user_service.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "orderservice", url = "http://localhost:8081")
public interface OrderClient {
    @GetMapping("/orders/user/{userId}")
    List<OrderDTO> getOrdersByUserId(@PathVariable("userId") Long userId);
}
