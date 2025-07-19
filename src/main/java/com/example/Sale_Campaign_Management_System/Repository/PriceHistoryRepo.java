package com.example.Sale_Campaign_Management_System.Repository;

import com.example.Sale_Campaign_Management_System.Model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PriceHistoryRepo extends JpaRepository<PriceHistory,Long> {

    @Query(value = "SELECT * FROM price_history WHERE product_id = :productId ORDER BY date DESC LIMIT 1", nativeQuery = true)
    PriceHistory findLatestByProductNative(@Param("productId") String productId);

}
