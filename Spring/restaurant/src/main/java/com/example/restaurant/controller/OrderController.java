package com.example.restaurant.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant.dto.OrderDTO;
import com.example.restaurant.entity.Order;
import com.example.restaurant.entity.Product;
import com.example.restaurant.repository.OrderRepository;
import com.example.restaurant.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequestMapping("/orders")
@CrossOrigin(origins="http://localhost:3000")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderRepository or;
    @Autowired
    private final ProductRepository pr;

    @GetMapping("")
    public ResponseEntity<?> getAllOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer user_id = Integer.parseInt(authentication.getName());
        String role = authentication.getAuthorities().iterator().next().getAuthority().substring(5);
        System.out.println(role);
        if (role.equals("ADMIN")) {
            return new ResponseEntity<>(or.findAll().stream().map(OrderDTO::Convert).toArray(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(or.findAllByUserId(user_id).stream().map(OrderDTO::Convert).toArray(), HttpStatus.OK);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Integer id) {
        return new ResponseEntity<>(or.findById(id).map(OrderDTO::Convert).orElse(null), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestBody ProductsWrapper productsWrapper) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer user_id = Integer.parseInt(authentication.getName());

        Order order = new Order();
        List<Product> products = productsWrapper.getProducts();
        order.setProducts(products);
        order.setUser_id(user_id);
        // order.setUser(ur.getById(14)); //TODO change to jwt tocen id

        order.setProducts(
            pr.findAllById(products.stream()
                                   .map(Product::getId)
                                   .collect(Collectors.toList())
                                    ));
        
        or.save(order);

        return new ResponseEntity<>(OrderDTO.Convert(order), HttpStatus.OK);
    }

    @PostMapping("/{id}/confirm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> confirmOrder(@PathVariable Integer id) {
        Order order = or.findById(id).orElse(null);
        if(order != null) {
            order.setConfirmed(true);
            or.save(order);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/{id}/paid")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> paidOrder(@PathVariable Integer id) {
        Order order = or.findById(id).orElse(null);
        if(order != null) {
            order.setPaid(true);
            or.save(order);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}

class ProductsWrapper {
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
