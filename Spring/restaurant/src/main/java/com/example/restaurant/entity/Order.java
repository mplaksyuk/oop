package com.example.restaurant.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "order", schema="public")
public class Order {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", updatable = false)
    private Integer user_id;

    @Column(name = "creation_date", updatable = true)
    private Timestamp creation_date = new Timestamp(new Date().getTime());

    @Column(name = "paid", updatable = true)
    private Boolean paid = false;

    @Column(name = "confirmed", updatable = true)
    private Boolean confirmed = false;

    @ManyToOne()
    @JoinColumn(name="user_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "order_product", 
        joinColumns = { @JoinColumn(name="order_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    List<Product> products = new ArrayList<>();
}
