package com.example.restaurant.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.restaurant.entity.Order;
import com.example.restaurant.entity.Product;
import com.example.restaurant.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDTO {
    private Integer id;
    private Integer user_id;
    private Timestamp creation_date;
    private Boolean paid;
    private Boolean confirmed;
    private User user;
    private List<Product> products = new ArrayList<>();

    public static OrderDTO Convert(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        // dto.setUser_id(order.getUser_id());
        dto.setCreation_date(order.getCreation_date());
        dto.setPaid(order.getPaid());
        dto.setConfirmed(order.getConfirmed());
        dto.setUser(order.getUser());
        dto.setProducts(order.getProducts());
        return dto;
    }
}
