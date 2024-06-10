package org.hse.software.construction.ticketservice.Service.Impl;

import org.hse.software.construction.ticketservice.Model.Order;
import org.hse.software.construction.ticketservice.Model.OrderStatus;
import org.hse.software.construction.ticketservice.Service.JwtService;
import org.hse.software.construction.ticketservice.Service.OrderService;
import org.hse.software.construction.ticketservice.Utility.OrderRequest;
import org.hse.software.construction.ticketservice.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private StationServiceImpl stationService;
    private JwtService jwtService;

    @Override
    @Transactional
    public Order createOrder(OrderRequest request, String token) {
        if (stationService.getStationById(request.getFromStationId()) == null &&
                stationService.getStationById(request.getToStationId()) == null) {
            throw new IllegalArgumentException("Cant find this stations");
        }
        if (Objects.equals(request.getFromStationId(), request.getToStationId())) {
            throw new IllegalArgumentException("From and to stations should be different");
        }
        if (!Objects.equals(jwtService.getIdFromToken(token).toString(), request.getUserId().toString())) {
            throw new IllegalArgumentException("Invalid user");
        }
        if (!jwtService.validateToken(token)) {
            throw new IllegalArgumentException("invalid token");
        }

        Order order = Order.builder()
                .userId(request.getUserId())
                .fromStationId(request.getFromStationId())
                .toStationId(request.getToStationId())
                .build();
        orderRepository.save(order);
        log.info("created order with id: " + order.getId());
        return order;
    }

    @Override
    @Transactional
    public Order getOrderById(UUID id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        }
        throw new IllegalArgumentException("no order with this id");
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findOrderByStatus(status);

    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }
}
