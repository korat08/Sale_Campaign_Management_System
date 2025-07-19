package com.example.Sale_Campaign_Management_System.Repository;

import com.example.Sale_Campaign_Management_System.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,String> {
}
