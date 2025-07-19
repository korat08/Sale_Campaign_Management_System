package com.example.Sale_Campaign_Management_System.Controller;


import com.example.Sale_Campaign_Management_System.DTOs.*;
import com.example.Sale_Campaign_Management_System.Service.CampaignScheduler;
import com.example.Sale_Campaign_Management_System.Service.CampaignService;
import com.example.Sale_Campaign_Management_System.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class Admin {

    @Autowired
    ProductService productService;

    @Autowired
    CampaignService campaignService;

    @Autowired
    CampaignScheduler campaignScheduler;

    @GetMapping("/welcome")
    public String hello(){
        return "welcome admin";
    }


    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse<?>> addProduct(@Valid @RequestBody AddProductRequest request){
        return productService.addProduct(request);
    }

    @PutMapping("/update-product/{productId}")
    public ResponseEntity<ApiResponse<?>> updateProduct(@Valid @RequestBody UpdateProductRequest request,@PathVariable String productId){
        return productService.updateProduct(request,productId);
    }

    @GetMapping("/get-product/{productId}")
    public ResponseEntity<ApiResponse<?>> getProduct(@PathVariable String productId){
        return productService.getProduct(productId);
    }

    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable String productId){
        return productService.deleteProduct(productId);
    }

    @PostMapping("/add-campaign")
    public ResponseEntity<ApiResponse<?>> addCampaign(@Valid @RequestBody CampaignRequestDTO requestDTO){
        return campaignService.addCampaign(requestDTO);
    }

    @GetMapping("/get-campaign/{campaignId}")
    public ResponseEntity<ApiResponse<?>> getCampaign(@PathVariable Long campaignId){
        return campaignService.getCampaign(campaignId);
    }

    @PutMapping("/update-campaign/{campaignId}")
    public ResponseEntity<ApiResponse<?>> updateCampaignDetails(@PathVariable Long campaignId,@Valid @RequestBody UpdateCampaignRequestDto requestDto){
        return campaignService.updateCampaign(campaignId,requestDto);
    }

    @DeleteMapping("/delete-campaign/{campaignId}")
    public ResponseEntity<ApiResponse<?>> deleteCampaign(@PathVariable Long campaignId){
        return campaignService.deleteCampaign(campaignId);
    }

    @GetMapping("/trigger-campaign-scheduler")
    public String triggerNow() {
        campaignScheduler.processCampaign();
        return "Scheduler triggered manually";
    }

    @GetMapping("/get-past-campaigns")
    public ResponseEntity<ApiResponse<?>> getPastCampaigns() {
        return campaignService.getPastCampaigns();
    }

    @GetMapping("/get-active-campaigns")
    public ResponseEntity<ApiResponse<?>> getActiveCampaigns() {
        return campaignService.getActiveCampaigns();
    }

    @GetMapping("/get-upcoming-campaigns")
    public ResponseEntity<ApiResponse<?>> getUpcomingCampaigns() {
        return campaignService.getUpcomingCampaigns();
    }




}
