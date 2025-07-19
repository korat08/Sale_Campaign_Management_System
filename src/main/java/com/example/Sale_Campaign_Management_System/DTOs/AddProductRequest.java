package com.example.Sale_Campaign_Management_System.DTOs;


import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String title;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(precision = 20,scale = 2)
    private BigDecimal mrp;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(precision = 20,scale = 2)
    private BigDecimal currentPrice;

    @NotNull
    @Min(value = 0)
    private Integer discount;

    @NotNull
    private Integer quantity;
}
