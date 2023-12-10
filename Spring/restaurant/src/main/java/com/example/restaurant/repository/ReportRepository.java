package com.example.restaurant.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.restaurant.entity.Product;
import com.example.restaurant.entity.enums.Unit;

public interface ReportRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT p.name AS name, COUNT(p) AS quantity, SUM(p.price) AS totalPrice " +
            "FROM Order o " +
            "JOIN o.products p " +
            "WHERE o.creation_date BETWEEN :from AND :thru AND o.paid = true " +
            "GROUP BY p.name " +
            "ORDER BY quantity DESC")
    List<PopularityProduct> topProducts(@Param("from") Timestamp from, @Param("thru") Timestamp thru);

    interface PopularityProduct {
        public String getName();
        public Long getQuantity();
        public Long getTotalPrice();
    }

    @Query(value = "SELECT i.name AS name, COUNT(*) AS quantity, SUM(pi.quantity) AS volume, i.unit AS unit, i.price / 100.0 * SUM(pi.quantity) AS totalPrice " +
            "FROM public.order o " +
            "INNER JOIN order_product op ON o.id = op.order_id " +
            "INNER JOIN product_ingredient pi ON op.product_id = pi.product_id " +
            "INNER JOIN ingredient i ON i.id = pi.ingredient_id " +
            "WHERE o.creation_date BETWEEN :from AND :thru AND o.paid = true " +
            "GROUP BY i.name, i.unit, i.price " +
            "ORDER BY quantity DESC", nativeQuery = true)
    List<SpendIngredient> topSpendIngredients(@Param("from") Timestamp from, @Param("thru") Timestamp thru);
    
    interface SpendIngredient {
        public String getName();

        public Long getQuantity();

        public Long getVolume();

        public Unit getUnit();

        public Double getTotalPrice();
    }
    
    @Query(value = "SELECT u.name AS name, COUNT(o) AS quantity, (SUM(p.price) / COUNT(p)) AS averageAmount " + 
        "FROM Order o " +
        "JOIN o.user u " +
        "JOIN o.products p " +
        "WHERE o.creation_date BETWEEN :from AND :thru AND o.paid = true " +
        "GROUP BY u.name " +
        "ORDER BY u.name, averageAmount DESC")
    List<TopUser> topUser(@Param("from") Timestamp from, @Param("thru") Timestamp thru);

    interface TopUser {
        public String getName();

        public Long getQuantity();

        public Long getAverageAmount();
    }

    @Query(value = "SELECT name, (sumPrice / sumTotal * 100 ) AS percent, sumTotal FROM " +
            "(SELECT p.id, p.name, SUM(p.price) AS sumPrice, SUM(t.totalPrice) AS sumTotal FROM public.order o " +
            "INNER JOIN order_product op ON op.order_id  = o.id " +
            "INNER JOIN product p ON p.id = op.product_id " +
            "INNER JOIN ( " +
            "SELECT o1.id, SUM(p1.price) AS totalPrice  FROM public.order o1 " +
            "INNER JOIN order_product op1 ON op1.order_id  = o1.id " +
            "INNER JOIN product p1 ON p1.id = op1.product_id " +
            "WHERE o1.creation_date BETWEEN :from AND :thru AND o1.paid = true " +
            "GROUP BY o1.id) t ON o.id = t.id " +
            "GROUP BY p.id, p.name) a " +
            "ORDER BY percent DESC", nativeQuery = true)
    List<ProductPercentage> productPercentage(@Param("from") Timestamp from, @Param("thru") Timestamp thru);

    interface ProductPercentage {
        public String getName();

        public Double getPercent();

        public Long getSumTotal();
    }
    
    @Query(value = "SELECT \n" + //
            "\tc.name,\n" + //
            "  c.count_of_products AS countOfProducts,\n" + //
            "  c.total_price AS productsRevenue,\n" + //
            "  c.total_ingredients_cost + c.total_work_cost AS primeCost,\n" + //
            "  (c.total_ingredients_cost + c.total_work_cost) * 0.82 AS finalProfit\n" + //
            "FROM\n" + //
            "(\n" + //
            "  SELECT \n" + //
            "    pr.name,\n" + //
            "    COUNT(pr) AS count_of_products,\n" + //
            "    SUM(pr.price) AS total_price,\n" + //
            "    SUM(pr.work_cost) AS total_work_cost,\n" + //
            "    SUM(pr.ingredients_cost) AS total_ingredients_cost\n" + //
            "  FROM public.order o\n" + //
            "  INNER JOIN order_product op \n" + //
            "    ON op.order_id = o.id\n" + //
            "  INNER JOIN (\n" + //
            "    SELECT \n" + //
            "      p.id,\n" + //
            "      p.name,\n" + //
            "      p.price,\n" + //
            "      p.creation_duration,\n" + //
            "      ROUND(SUM((pi.quantity * i.price / 100.0)), 2) AS ingredients_cost,\n" + //
            "      ROUND(EXTRACT(EPOCH FROM p.creation_duration) / 60 * 1.5, 0) AS work_cost\n" + //
            "    FROM product p\n" + //
            "    INNER JOIN product_ingredient pi \n" + //
            "      ON pi.product_id = p.id\n" + //
            "    INNER JOIN ingredient i \n" + //
            "      ON pi.ingredient_id = i.id\n" + //
            "    GROUP BY \n" + //
            "      p.id,\n" + //
            "      p.name,\n" + //
            "      p.price,\n" + //
            "      p.creation_duration,\n" + //
            "      work_cost\n" + //
            "  ) pr \n" + //
            "    ON op.product_id = pr.id\n" + //
            "  WHERE o.creation_date BETWEEN :from AND :thru AND o.paid = true\n" + //
            "  GROUP BY \n" + //
            "  \tpr.name\n" + //
            ") c\n" + //
            "ORDER BY finalProfit DESC", nativeQuery = true)
    List<ProductRevenue> productRevenues(@Param("from") Timestamp from, @Param("thru") Timestamp thru);
    
    public interface ProductRevenue {
        public String getName();

        public Long getCountOfProducts();

        public Double getProductsRevenue();

        public Double getPrimeCost();

        public Double getFinalProfit();
    }
}

