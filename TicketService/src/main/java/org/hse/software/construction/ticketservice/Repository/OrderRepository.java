package org.hse.software.construction.ticketservice.Repository;

import org.hse.software.construction.ticketservice.Model.Order;
import org.hse.software.construction.ticketservice.Model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findOrderByStatus(OrderStatus status);
}
