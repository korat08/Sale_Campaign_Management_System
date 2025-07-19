package com.example.Sale_Campaign_Management_System.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T>{
    private Boolean isSuccess;
    private String message;
    private T data;
}
