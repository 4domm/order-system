package org.hse.software.construction.ticketservice.Controller;

import org.hse.software.construction.ticketservice.Model.Order;
import org.hse.software.construction.ticketservice.Service.Impl.OrderServiceImpl;
import org.hse.software.construction.ticketservice.Utility.OrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class OrderController {
    private OrderServiceImpl orderService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest request, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            orderService.createOrder(request, token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Order created");
    }

    @GetMapping("/{orderId}")
    ResponseEntity<Order> getOrderById(@PathVariable UUID orderId) {
        try {
            return ResponseEntity.ok(orderService.getOrderById(orderId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
