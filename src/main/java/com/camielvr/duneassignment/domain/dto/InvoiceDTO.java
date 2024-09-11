package com.camielvr.duneassignment.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
public class InvoiceDTO {
    private Long invoiceId;
    private Long orderId;
    private String customerName;
    private BigDecimal pricePerQuantity;
    private String quantityType;
    private Integer quantity;
    private BigDecimal costWithoutTax;
    private BigDecimal tax;
    private BigDecimal taxCost;
    private BigDecimal totalCost;
    private String description;
}
