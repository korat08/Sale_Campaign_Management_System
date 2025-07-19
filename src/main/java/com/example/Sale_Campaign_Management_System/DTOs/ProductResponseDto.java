package com.example.Sale_Campaign_Management_System.DTOs;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private String id;
    private String title;
    private BigDecimal mrp;
    private BigDecimal currentPrice;
    private int discount;
    private int quantity;
}

