package com.camielvr.duneassignment.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
public class SpiceOrderDTO {
    private Long id;
    private String customerName;
    private Integer quantity;
    private String orderDate;
}
