package com.example.Sale_Campaign_Management_System.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    private String id;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(precision = 20,scale = 2,nullable = false)
    private BigDecimal mrp;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(precision = 20,scale = 2,nullable = false)
    private BigDecimal currentPrice;

    @NotNull
    @Min(value = 0)
    @Column(nullable = false)
    private Integer discount;

    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<PriceHistory> priceHistories = new ArrayList<>();

}
