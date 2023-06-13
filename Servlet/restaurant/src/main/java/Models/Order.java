package Models;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Order {
    private Integer id;
    private User user = new User();
    private List<Product> products;
    private Timestamp creation_date;
    private Boolean paid, confirmed;


    public void copy(Order order) {
        this.id = order.getId();
        this.user.copy(order.getUser());
        this.products = order.getProducts();
        this.creation_date = order.getCreation_date();
        this.paid = order.getPaid();
        this.confirmed = order.getConfirmed();
    }
}
