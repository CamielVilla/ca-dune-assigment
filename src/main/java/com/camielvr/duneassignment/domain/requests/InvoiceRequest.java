package com.camielvr.duneassignment.domain.requests;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequest {
    private String customerName;
    private BigDecimal pricePerQuantity;
    private Integer quantity;
    private BigDecimal tax;
    private String description;
}
