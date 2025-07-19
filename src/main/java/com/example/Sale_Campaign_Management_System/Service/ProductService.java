package com.example.Sale_Campaign_Management_System.Service;

import com.example.Sale_Campaign_Management_System.DTOs.AddProductRequest;
import com.example.Sale_Campaign_Management_System.DTOs.ApiResponse;
import com.example.Sale_Campaign_Management_System.DTOs.ProductResponseDto;
import com.example.Sale_Campaign_Management_System.DTOs.UpdateProductRequest;
import com.example.Sale_Campaign_Management_System.Model.PriceHistory;
import com.example.Sale_Campaign_Management_System.Model.Product;
import com.example.Sale_Campaign_Management_System.Repository.PriceHistoryRepo;
import com.example.Sale_Campaign_Management_System.Repository.ProductRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    PriceHistoryRepo priceHistoryRepo;

    public ResponseEntity<ApiResponse<?>> addProduct(@Valid AddProductRequest request) {

        Product product = Product.builder()
                .id(request.getId())
                .title(request.getTitle())
                .mrp(request.getMrp())
                .currentPrice(request.getCurrentPrice())
                .quantity(request.getQuantity())
                .discount(request.getDiscount())
                .build();

        Product product1 = productRepo.save(product);

        PriceHistory priceHistory = PriceHistory.builder()
                .price(request.getCurrentPrice())
                .discount(request.getDiscount())
                .reason("Initial Setup")
                .date(LocalDate.now())
                .product(product1)
                .build();

        priceHistoryRepo.save(priceHistory);


        return new ResponseEntity<>(new ApiResponse<>(true,"Add SuccessFully",product1), HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<?>> updateProduct(UpdateProductRequest request,String productId){


        Product product = productRepo.findById(productId).orElse(null);

        if(product == null){
            return new ResponseEntity<>(new ApiResponse<>(false,"Product Not Found",null), HttpStatus.BAD_REQUEST);
        }


        product.setTitle(request.getTitle());
        product.setMrp(request.getMrp());
        product.setCurrentPrice(request.getCurrentPrice());
        product.setDiscount(request.getDiscount());
        product.setQuantity(request.getQuantity());

        Product product1 = productRepo.save(product);

        PriceHistory priceHistory = PriceHistory.builder()
                .price(request.getCurrentPrice())
                .discount(request.getDiscount())
                .reason("Initial Setup")
                .date(LocalDate.now())
                .product(product1)
                .build();

        priceHistoryRepo.save(priceHistory);

        return new ResponseEntity<>(new ApiResponse<>(true,"updated SuccessFully",product1), HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<?>> getProduct(String productId){
        Product product = productRepo.findById(productId).orElse(null);

        if(product == null){
            return new ResponseEntity<>(new ApiResponse<>(false,"Product Not Found",null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse<>(true,"fetched SuccessFully",product), HttpStatus.OK);
    }


    public ResponseEntity<ApiResponse<?>> deleteProduct(String productId){
        Product product = productRepo.findById(productId).orElse(null);

        if(product == null){
            return new ResponseEntity<>(new ApiResponse<>(false,"Product Not Found",null), HttpStatus.BAD_REQUEST);
        }

        productRepo.deleteById(productId);

        return new ResponseEntity<>(new ApiResponse<>(true,"deleted SuccessFully",product), HttpStatus.OK);
    }

    public Page<ProductResponseDto> getPageProduct(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page,pageSize, Sort.by("title"));

        Page<Product> productPage = productRepo.findAll(pageable);

        return productPage.map(this::mapToDto);
    }

    public ProductResponseDto mapToDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .mrp(product.getMrp())
                .currentPrice(product.getCurrentPrice())
                .discount(product.getDiscount())
                .quantity(product.getQuantity())
                .build();
    }



}
