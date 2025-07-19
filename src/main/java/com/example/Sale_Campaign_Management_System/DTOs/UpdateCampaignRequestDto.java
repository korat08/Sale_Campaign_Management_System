package com.example.Sale_Campaign_Management_System.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCampaignRequestDto {

    private LocalDate startDate;

    private LocalDate endDate;

    private String title;

    private List<CampaignProductDTO> campaignProductDTOList = new ArrayList<>();
}
