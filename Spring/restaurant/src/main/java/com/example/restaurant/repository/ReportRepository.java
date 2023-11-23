package com.example.restaurant.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.restaurant.entity.Product;

public interface ReportRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT p.name AS name, COUNT(p) AS quantity, SUM(p.price) AS totalPrice " +
            "FROM Order o " +
            "JOIN o.products p " +
            "WHERE o.creation_date BETWEEN :from AND :thru " +
            "GROUP BY p.name " +
            "ORDER BY quantity DESC")
    List<PopularityProduct> topProducts(@Param("from") Timestamp from, @Param("thru") Timestamp thru);

    interface PopularityProduct {
        public String getName();
        public Long getQuantity();
        public Long getTotalPrice();
    }
    
}
