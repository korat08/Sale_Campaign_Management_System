package com.example.Sale_Campaign_Management_System.Repository;


import com.example.Sale_Campaign_Management_System.Model.Campaign;
import com.example.Sale_Campaign_Management_System.Model.CampaignProduct;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampaignRepo extends JpaRepository<Campaign,Long> {

    @Query(value = "select c from Campaign c where c.endDate < CURRENT_DATE")
    List<Campaign> findPastCampaigns();

    @Query(value = "select c from Campaign c where c.startDate <= CURRENT_DATE and c.endDate >= CURRENT_DATE")
    List<Campaign> findActiveCampaigns();

    @Query(value = "select c from Campaign c where c.startDate > CURRENT_DATE")
    List<Campaign> findUpcomingCampaigns();
}
