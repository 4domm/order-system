package org.hse.software.construction.ticketservice.Service.Impl;

import org.hse.software.construction.ticketservice.Model.Order;
import org.hse.software.construction.ticketservice.Model.OrderStatus;
import org.hse.software.construction.ticketservice.Service.OrderSimulateService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class OrderSimulateServiceImpl implements OrderSimulateService {

    private final OrderServiceImpl orderService;

    @Override
    @Scheduled(fixedRate = 5000)
    @Transactional
    public void processOrders() {
        List<Order> readyOrders = orderService.getOrdersByStatus(OrderStatus.WAITING);
        for (Order order : readyOrders) {
            boolean isSuccess = Math.random() < 0.5;
            if (isSuccess) {
                order.setStatus(OrderStatus.SUCCESSFUL);
            } else {
                order.setStatus(OrderStatus.REJECTED);
            }
            orderService.updateOrder(order);
        }
    }
}
