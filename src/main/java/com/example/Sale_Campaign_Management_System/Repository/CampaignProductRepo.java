package com.example.Sale_Campaign_Management_System.Repository;

import com.example.Sale_Campaign_Management_System.Model.CampaignProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignProductRepo extends JpaRepository<CampaignProduct,Long> {

    CampaignProduct findByCampaign_IdAndProduct_Id(Long campaignId, String productId);
}
