package com.example.Sale_Campaign_Management_System.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignProductDTO {

    @NotBlank
    private String productId;

    @NotNull
    private Integer discount;
}
