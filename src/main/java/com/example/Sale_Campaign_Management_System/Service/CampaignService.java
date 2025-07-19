package com.example.Sale_Campaign_Management_System.Service;

import com.example.Sale_Campaign_Management_System.DTOs.*;
import com.example.Sale_Campaign_Management_System.Model.Campaign;
import com.example.Sale_Campaign_Management_System.Model.CampaignProduct;
import com.example.Sale_Campaign_Management_System.Model.Product;
import com.example.Sale_Campaign_Management_System.Repository.CampaignProductRepo;
import com.example.Sale_Campaign_Management_System.Repository.CampaignRepo;
import com.example.Sale_Campaign_Management_System.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService {

    @Autowired
    CampaignRepo campaignRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CampaignProductRepo campaignProductRepo;

    @Autowired
    CampaignScheduler campaignScheduler;

    public ResponseEntity<ApiResponse<?>> addCampaign(CampaignRequestDTO requestDTO) {

        Campaign campaign = new Campaign();

        campaign.setTitle(requestDTO.getTitle());
        campaign.setStartDate(requestDTO.getStartDate());
        campaign.setEndDate(requestDTO.getEndDate());

        List<CampaignProduct> campaignProductList =  new ArrayList<>();

        for(CampaignProductDTO p : requestDTO.getCampaignProductList()){

            Product product = productRepo.findById(p.getProductId()).orElse(null);

            if(product == null){
                return new ResponseEntity<>(new ApiResponse<>(false,"Product Not Found ",p.getProductId()), HttpStatus.BAD_REQUEST);
            }


            CampaignProduct campaignProduct = new CampaignProduct();

            campaignProduct.setCampaign(campaign);
            campaignProduct.setProduct(product);
            campaignProduct.setDiscount(p.getDiscount());

            campaignProductList.add(campaignProduct);
        }

        campaign.setCampaignProductList(campaignProductList);

        Campaign campaign1 = campaignRepo.save(campaign);

        CampaignResponseDto campaignResponseDto = new CampaignResponseDto();

        campaignResponseDto.setCampaignId(campaign1.getId());
        campaignResponseDto.setTitle(campaign1.getTitle());
        campaignResponseDto.setEndDate(campaign1.getEndDate());
        campaignResponseDto.setStartDate(campaign1.getStartDate());


        List<ProductsOfCampaign> productsOfCampaignList = new ArrayList<>();

        for(CampaignProduct p : campaign1.getCampaignProductList()){
            ProductsOfCampaign  productsOfCampaign = new ProductsOfCampaign(p.getProduct().getId(),p.getDiscount());

            productsOfCampaignList.add(productsOfCampaign);
        }

        campaignResponseDto.setProductsOfCampaignList(productsOfCampaignList);

        return new ResponseEntity<>(new ApiResponse<>(true,"Added SuccessFully",campaignResponseDto),HttpStatus.CREATED);

    }

    public ResponseEntity<ApiResponse<?>> getCampaign(Long campaignId) {

        if(!campaignRepo.existsById(campaignId)){
            return new ResponseEntity<>(new ApiResponse<>(false,"Campaign Not Found",null),HttpStatus.BAD_REQUEST);
        }

        Campaign campaign = campaignRepo.findById(campaignId).orElse(null);

        if(campaign == null){
            return new ResponseEntity<>(new ApiResponse<>(false,"Campaign Not Found",null),HttpStatus.BAD_REQUEST);
        }

        CampaignResponseDto campaignResponseDto = new CampaignResponseDto();

        campaignResponseDto.setCampaignId(campaign.getId());
        campaignResponseDto.setTitle(campaign.getTitle());
        campaignResponseDto.setEndDate(campaign.getEndDate());
        campaignResponseDto.setStartDate(campaign.getStartDate());


        List<ProductsOfCampaign> productsOfCampaignList = new ArrayList<>();

        for(CampaignProduct p : campaign.getCampaignProductList()){
            ProductsOfCampaign  productsOfCampaign = new ProductsOfCampaign(p.getProduct().getId(),p.getDiscount());

            productsOfCampaignList.add(productsOfCampaign);
        }

        campaignResponseDto.setProductsOfCampaignList(productsOfCampaignList);

        return new ResponseEntity<>(new ApiResponse<>(true,"Fetched Successfully",campaignResponseDto),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse<?>> updateCampaign(Long campaignId, UpdateCampaignRequestDto requestDto) {

        if(!campaignRepo.existsById(campaignId)){
            return new ResponseEntity<>(new ApiResponse<>(false,"Campaign Not Found",null),HttpStatus.BAD_REQUEST);
        }

        Campaign campaign = campaignRepo.findById(campaignId).orElse(null);


        if(campaign == null){
            return new ResponseEntity<>(new ApiResponse<>(false,"Campaign Not Found",null),HttpStatus.BAD_REQUEST);
        }

        if(requestDto.getStartDate() != null){
            campaign.setStartDate(requestDto.getStartDate());
        }

        if(requestDto.getEndDate() != null){
            campaign.setEndDate(requestDto.getEndDate());
        }

        if(requestDto.getTitle() != null){
            campaign.setTitle(requestDto.getTitle());
        }

        for(CampaignProductDTO product : requestDto.getCampaignProductDTOList()){
            CampaignProduct campaignProduct = campaignProductRepo.findByCampaign_IdAndProduct_Id(campaign.getId(),product.getProductId());


            campaignProduct.setDiscount(product.getDiscount());
            campaignProductRepo.save(campaignProduct);
        }

        Campaign campaign1 = campaignRepo.save(campaign);

        campaignScheduler.processCampaign();

        return new ResponseEntity<>(new ApiResponse<>(true,"Success",campaign1),HttpStatus.OK);

    }

    public ResponseEntity<ApiResponse<?>> deleteCampaign(Long campaignId) {
        if(!campaignRepo.existsById(campaignId)){
            return new ResponseEntity<>(new ApiResponse<>(false,"Campaign Not Found",null),HttpStatus.BAD_REQUEST);
        }

        Campaign campaign = campaignRepo.findById(campaignId).orElse(null);


        if(campaign == null){
            return new ResponseEntity<>(new ApiResponse<>(false,"Campaign Not Found",null),HttpStatus.BAD_REQUEST);
        }

        CampaignResponseDto campaignResponseDto = new CampaignResponseDto();

        campaignResponseDto.setCampaignId(campaign.getId());
        campaignResponseDto.setTitle(campaign.getTitle());
        campaignResponseDto.setEndDate(campaign.getEndDate());
        campaignResponseDto.setStartDate(campaign.getStartDate());


        List<ProductsOfCampaign> productsOfCampaignList = new ArrayList<>();

        for(CampaignProduct p : campaign.getCampaignProductList()){
            ProductsOfCampaign  productsOfCampaign = new ProductsOfCampaign(p.getProduct().getId(),p.getDiscount());

            productsOfCampaignList.add(productsOfCampaign);
        }

        campaignResponseDto.setProductsOfCampaignList(productsOfCampaignList);

        campaignRepo.deleteById(campaignId);

        campaignScheduler.processCampaign();

        return new ResponseEntity<>(new ApiResponse<>(true,"Deleted Successfully",campaignResponseDto),HttpStatus.OK);
    }


    public ResponseEntity<ApiResponse<?>> getPastCampaigns() {

        List<Campaign> campaignList = campaignRepo.findPastCampaigns();

        List<CampaignResponseDto> campaignResponseDtoList = new ArrayList<>();

        for(Campaign campaign : campaignList){

            CampaignResponseDto campaignResponseDto = new CampaignResponseDto();

            campaignResponseDto.setCampaignId(campaign.getId());
            campaignResponseDto.setTitle(campaign.getTitle());
            campaignResponseDto.setEndDate(campaign.getEndDate());
            campaignResponseDto.setStartDate(campaign.getStartDate());


            List<ProductsOfCampaign> productsOfCampaignList = new ArrayList<>();

            for(CampaignProduct p : campaign.getCampaignProductList()){
                ProductsOfCampaign  productsOfCampaign = new ProductsOfCampaign(p.getProduct().getId(),p.getDiscount());

                productsOfCampaignList.add(productsOfCampaign);
            }

            campaignResponseDto.setProductsOfCampaignList(productsOfCampaignList);

            campaignResponseDtoList.add(campaignResponseDto);
        }

        return new ResponseEntity<>(new ApiResponse<>(true,"fetched SuccessFully",campaignResponseDtoList),HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<?>> getActiveCampaigns() {
        List<Campaign> campaignList = campaignRepo.findActiveCampaigns();

        List<CampaignResponseDto> campaignResponseDtoList = new ArrayList<>();

        for(Campaign campaign : campaignList){

            CampaignResponseDto campaignResponseDto = new CampaignResponseDto();

            campaignResponseDto.setCampaignId(campaign.getId());
            campaignResponseDto.setTitle(campaign.getTitle());
            campaignResponseDto.setEndDate(campaign.getEndDate());
            campaignResponseDto.setStartDate(campaign.getStartDate());


            List<ProductsOfCampaign> productsOfCampaignList = new ArrayList<>();

            for(CampaignProduct p : campaign.getCampaignProductList()){
                ProductsOfCampaign  productsOfCampaign = new ProductsOfCampaign(p.getProduct().getId(),p.getDiscount());

                productsOfCampaignList.add(productsOfCampaign);
            }

            campaignResponseDto.setProductsOfCampaignList(productsOfCampaignList);

            campaignResponseDtoList.add(campaignResponseDto);
        }


        return new ResponseEntity<>(new ApiResponse<>(true,"fetched SuccessFully",campaignResponseDtoList),HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<?>> getUpcomingCampaigns() {
        List<Campaign> campaignList = campaignRepo.findUpcomingCampaigns();

        List<CampaignResponseDto> campaignResponseDtoList = new ArrayList<>();

        for(Campaign campaign : campaignList){

            CampaignResponseDto campaignResponseDto = new CampaignResponseDto();

            campaignResponseDto.setCampaignId(campaign.getId());
            campaignResponseDto.setTitle(campaign.getTitle());
            campaignResponseDto.setEndDate(campaign.getEndDate());
            campaignResponseDto.setStartDate(campaign.getStartDate());


            List<ProductsOfCampaign> productsOfCampaignList = new ArrayList<>();

            for(CampaignProduct p : campaign.getCampaignProductList()){
                ProductsOfCampaign  productsOfCampaign = new ProductsOfCampaign(p.getProduct().getId(),p.getDiscount());

                productsOfCampaignList.add(productsOfCampaign);
            }

            campaignResponseDto.setProductsOfCampaignList(productsOfCampaignList);

            campaignResponseDtoList.add(campaignResponseDto);
        }

        return new ResponseEntity<>(new ApiResponse<>(true,"fetched SuccessFully",campaignResponseDtoList),HttpStatus.OK);
    }


}
