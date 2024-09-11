package com.camielvr.duneassignment.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private Integer quantity;
    private LocalDateTime orderDate;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Invoice invoice;
    @Builder.Default
    private String description = "Spice";
    @Builder.Default
    private String quantityType = "Kg";
}
