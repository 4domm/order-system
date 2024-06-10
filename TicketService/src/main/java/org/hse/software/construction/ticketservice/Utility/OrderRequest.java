package org.hse.software.construction.ticketservice.Utility;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class OrderRequest {
    private UUID userId;
    private UUID fromStationId;
    private UUID toStationId;
}
