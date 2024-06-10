package org.hse.software.construction.ticketservice.Service;
import org.hse.software.construction.ticketservice.Model.Order;
import org.hse.software.construction.ticketservice.Model.OrderStatus;
import org.hse.software.construction.ticketservice.Utility.OrderRequest;

import java.util.List;
import java.util.UUID;
public interface OrderService {
    Order createOrder(OrderRequest request, String token);
    Order getOrderById(UUID id);
    List<Order> getOrdersByStatus(OrderStatus status);
    Order updateOrder(Order order);
}
