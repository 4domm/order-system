package org.hse.software.construction.ticketservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;
    private UUID fromStationId;
    private UUID toStationId;
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @PrePersist
    private void init() {
        status = OrderStatus.WAITING;
    }
}
