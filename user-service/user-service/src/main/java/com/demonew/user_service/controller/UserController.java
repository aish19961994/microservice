package com.demonew.user_service.controller;

import com.demonew.user_service.dto.OrderDTO;
import com.demonew.user_service.entity.User;
import com.demonew.user_service.feign.OrderClient;
import com.demonew.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final OrderClient orderClient;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserWithOrders(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return ResponseEntity.notFound().build();

        List<OrderDTO> orders = orderClient.getOrdersByUserId(id);
        Map<String, Object> response = new HashMap<>();
        response.put("user", user.get());
        response.put("orders", orders);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(updatedUser.getName());
                    existingUser.setEmail(updatedUser.getEmail());
                    return ResponseEntity.ok(userRepository.save(existingUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(id);
                    return ResponseEntity.ok("User deleted successfully");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
