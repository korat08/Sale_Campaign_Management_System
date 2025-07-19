package com.example.Sale_Campaign_Management_System.DTOs;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductsOfCampaign {

    private String productId;

    private Integer discount;
}
