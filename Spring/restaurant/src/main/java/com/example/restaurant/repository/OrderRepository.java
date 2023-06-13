package com.example.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restaurant.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserId(Integer user_id);
}
