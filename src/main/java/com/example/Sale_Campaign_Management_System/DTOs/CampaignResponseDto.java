package com.example.Sale_Campaign_Management_System.DTOs;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignResponseDto {

    private Long campaignId;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<ProductsOfCampaign> productsOfCampaignList = new ArrayList<>();

}
