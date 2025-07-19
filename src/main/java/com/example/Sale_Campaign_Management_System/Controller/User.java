package com.example.Sale_Campaign_Management_System.Controller;

import com.example.Sale_Campaign_Management_System.DTOs.ApiResponse;
import com.example.Sale_Campaign_Management_System.DTOs.CampaignRequestDTO;
import com.example.Sale_Campaign_Management_System.DTOs.ProductResponseDto;
import com.example.Sale_Campaign_Management_System.DTOs.SignUpRequest;
import com.example.Sale_Campaign_Management_System.Model.Product;
import com.example.Sale_Campaign_Management_System.Service.MyUserService;
import com.example.Sale_Campaign_Management_System.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class User {

    @Autowired
    MyUserService myUserService;

    @Autowired
    ProductService productService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome user";
    }

    @PutMapping("/update-profile")
    public ResponseEntity<ApiResponse<?>> updateUser(@Valid @RequestBody SignUpRequest request, Authentication authentication){
        return myUserService.updateUser(request,authentication.getName());
    }

    @GetMapping("/get-profile")
    public ResponseEntity<ApiResponse<?>> getUserDetails(Authentication authentication){
        return myUserService.getUserDetails(authentication.getName());
    }

    @GetMapping("/page-product")
    public PagedModel<EntityModel<ProductResponseDto>> getPageProduct(@RequestParam int page,
                                                           @RequestParam int pageSize,
                                                           PagedResourcesAssembler<ProductResponseDto> assembler
    ){


        Page<ProductResponseDto> dtoPage = productService.getPageProduct(page,pageSize);

        return assembler.toModel(dtoPage);
    }

}








