package com.example.Sale_Campaign_Management_System.Service;

import com.example.Sale_Campaign_Management_System.Model.Campaign;
import com.example.Sale_Campaign_Management_System.Model.CampaignProduct;
import com.example.Sale_Campaign_Management_System.Model.PriceHistory;
import com.example.Sale_Campaign_Management_System.Model.Product;
import com.example.Sale_Campaign_Management_System.Repository.CampaignRepo;
import com.example.Sale_Campaign_Management_System.Repository.PriceHistoryRepo;
import com.example.Sale_Campaign_Management_System.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class CampaignScheduler {

    @Autowired
    CampaignRepo campaignRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    PriceHistoryRepo priceHistoryRepo;

    @Scheduled(cron = "0 35 10 * * *",zone = "Asia/Kolkata")
    public void processCampaign(){

        LocalDate date = LocalDate.now();

        List<Campaign> campaigns = campaignRepo.findAll();

        for(Campaign campaign : campaigns){
            if(campaign.getStartDate().isEqual(date)){
                startCampaign(campaign);
            } else if (campaign.getEndDate().plusDays(1).isEqual(date)) {
                endCampaign(campaign);
            }
        }
    }

    private void startCampaign(Campaign campaign){

        for(CampaignProduct campaignProduct : campaign.getCampaignProductList()){

            Product product = campaignProduct.getProduct();

            priceHistoryRepo.save(PriceHistory.builder()
                            .product(product)
                            .price(product.getCurrentPrice())
                            .discount(product.getDiscount())
                            .date(LocalDate.now())
                            .reason(campaign.getTitle())
                            .build());

            int discount = campaignProduct.getDiscount();
            BigDecimal mrp = product.getMrp();

            BigDecimal newPrice = mrp.subtract(
                    mrp.multiply(BigDecimal.valueOf(discount))
                            .divide(BigDecimal.valueOf(100))
            );

            product.setCurrentPrice(newPrice);
            product.setDiscount(discount);

            productRepo.save(product);
        }
    }

    private void endCampaign(Campaign campaign) {
        for(CampaignProduct campaignProduct : campaign.getCampaignProductList()){

            Product product = campaignProduct.getProduct();

            PriceHistory latestPrice = priceHistoryRepo.findLatestByProductNative(product.getId());

            if(latestPrice != null){
                product.setCurrentPrice(latestPrice.getPrice());
                product.setDiscount(latestPrice.getDiscount());
                productRepo.save(product);
            }
        }
    }


}
