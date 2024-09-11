package com.camielvr.duneassignment.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private BigDecimal pricePerKilogram;
    private Integer quantity;
    private BigDecimal costWithoutTax;
    private BigDecimal tax;
    private BigDecimal taxCost;
    private BigDecimal totalCost;
    private String description;
}
