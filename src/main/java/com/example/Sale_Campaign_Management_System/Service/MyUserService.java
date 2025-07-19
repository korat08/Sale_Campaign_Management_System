package com.example.Sale_Campaign_Management_System.Service;

import com.example.Sale_Campaign_Management_System.DTOs.ApiResponse;
import com.example.Sale_Campaign_Management_System.DTOs.SignInRequest;
import com.example.Sale_Campaign_Management_System.DTOs.SignUpRequest;
import com.example.Sale_Campaign_Management_System.Model.CustomUser;
import com.example.Sale_Campaign_Management_System.Model.CustomUserDetails;
import com.example.Sale_Campaign_Management_System.Repository.CustomUserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MyUserService {
    @Autowired
    CustomUserRepo customUserRepo;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<?> createUser(@Valid SignUpRequest request) {


        if (customUserRepo.existsByEmail(request.getEmail())) {
            return new ResponseEntity<>(Map.of("message","Email already exists"), HttpStatus.BAD_REQUEST);
        }else if (customUserRepo.existsByPhoneNumber(request.getPhoneNumber())) {
            return new ResponseEntity<>(Map.of("message","Phone Number already exists"),HttpStatus.BAD_REQUEST);
        } else if (customUserRepo.existsByUserName(request.getUserName())) {
            return new ResponseEntity<>(Map.of("message","User Name already exists"),HttpStatus.BAD_REQUEST);
        }

        CustomUser customUser = CustomUser.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .city(request.getCity())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        customUserRepo.save(customUser);

        return new ResponseEntity<>(Map.of("Message :","User created SuccessFully.."), HttpStatus.CREATED);
    }

    public ResponseEntity<?> verify(@Valid SignInRequest request) {
        try{

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(),request.getPassword())
            );

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            String token = jwtService.getToken(request.getUserName(),customUserDetails.getCustomUser().getRole());

            return new ResponseEntity<>(Map.of("token :",token),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(Map.of("Message :",e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ApiResponse<?>> updateUser(@Valid SignUpRequest request, String name) {

        CustomUser customUser = customUserRepo.findByUserName(name);

        if (customUser == null) {
            return new ResponseEntity<>(new ApiResponse<>(false, "User not found", null), HttpStatus.NOT_FOUND);
        }

        CustomUser emailOwner = customUserRepo.findByEmail(request.getEmail());
        if (emailOwner != null && !emailOwner.getId().equals(customUser.getId())) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Email already exists", null), HttpStatus.BAD_REQUEST);
        }

        CustomUser phoneOwner = customUserRepo.findByPhoneNumber(request.getPhoneNumber());
        if (phoneOwner != null && !phoneOwner.getId().equals(customUser.getId())) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Phone Number already exists", null), HttpStatus.BAD_REQUEST);
        }

        CustomUser nameOwner = customUserRepo.findByUserName(request.getUserName());
        if (nameOwner != null && !nameOwner.getId().equals(customUser.getId())) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Name already exists", null), HttpStatus.BAD_REQUEST);
        }

        customUser.setUserName(request.getUserName());
        customUser.setEmail(request.getEmail());
        customUser.setCity(request.getCity());
        customUser.setPassword(passwordEncoder.encode(request.getPassword()));
        customUser.setPhoneNumber(request.getPhoneNumber());
        customUser.setRole(request.getRole());

        try {
            customUserRepo.save(customUser);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Failed to update user: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(new ApiResponse<>(true,"Updated SuccessFully",customUser),HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<?>> getUserDetails(String userName) {

        CustomUser customUser = customUserRepo.findByUserName(userName);

        return new ResponseEntity<>(new ApiResponse<>(true,"Fetched",customUser),HttpStatus.OK);
    }
}
