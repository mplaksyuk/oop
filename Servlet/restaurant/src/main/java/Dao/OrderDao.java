package Dao;

import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Models.Order;
import Models.Product;
import Models.User;
import abstr.DaoAbstract;

public class OrderDao extends DaoAbstract {

    // private static final Logger log = Logger.getLogger(OrderDao.class);

    public static List<Order> getOrders() {
        String query = "SELECT * FROM public.order";
        
        List<Order> orders = new ArrayList<>();
        
        connect(query, (statement) -> {
            ResultSet rSet = statement.executeQuery();

            while(rSet.next()) {
                Integer order_id = rSet.getInt("id");
                Integer user_id = rSet.getInt("user_id");
                List<Product> products = ProductDao.getProductsByOrderId(order_id);
                Timestamp creation_date = rSet.getTimestamp("creation_date");

                Boolean paid = rSet.getBoolean("paid");
                Boolean confirmed = rSet.getBoolean("confirmed");

                User user = UserDao.getUser(user_id);

                orders.add(new Order(order_id, user, products, creation_date, paid, confirmed));
            }
        });

        return orders;
    }

    public static List<Order> getOrders(Integer user_id) {
        String query = "SELECT * FROM public.order WHERE user_id = ?";

        List<Order> orders = new ArrayList<>();

        connect(query, (statement) -> {
            statement.setInt(1, user_id);

            ResultSet rSet = statement.executeQuery();

            while(rSet.next()) {
                Integer order_id = rSet.getInt("id");
                List<Product> products = ProductDao.getProductsByOrderId(order_id);
                Timestamp creation_date = rSet.getTimestamp("creation_date");

                Boolean paid = rSet.getBoolean("paid");
                Boolean confirmed = rSet.getBoolean("confirmed");

                User user = UserDao.getUser(user_id);

                orders.add(new Order(order_id, user, products, creation_date, paid, confirmed));
            }
        });

        return orders;
    }

    public static Order getOrderById(Integer order_id) {
        String query = "SELECT * FROM public.order WHERE id = ?";

        Order order = new Order();

        connect(query, (statement) -> {
            statement.setInt(1, order_id);

            ResultSet rSet = statement.executeQuery();

            if(rSet.next()) {
                List<Product> products = ProductDao.getProductsByOrderId(order_id);

                order.setId( order_id );
                order.setUser( UserDao.getUser( rSet.getInt("user_id") ) );
                order.setProducts( products );
                order.setCreation_date( rSet.getTimestamp("creation_date") );
                order.setPaid( rSet.getBoolean("paid") );
                order.setConfirmed( rSet.getBoolean("confirmed") );
            }
        });

        return order;
    }


    public static Order getOrderById(Integer user_id, Integer order_id) {
        String query = "SELECT * FROM public.order WHERE id = ? AND user_id = ?";

        Order order = new Order();

        connect(query, (statement) -> {
            statement.setInt(1, order_id);
            statement.setInt(2, user_id);

            ResultSet rSet = statement.executeQuery();

            if(rSet.next()) {
                List<Product> products = ProductDao.getProductsByOrderId(order_id);

                order.setId( order_id );
                order.setUser( UserDao.getUser( user_id ) );
                order.setProducts( products );
                order.setCreation_date( rSet.getTimestamp("creation_date") );
                order.setPaid( rSet.getBoolean("paid") );
                order.setConfirmed( rSet.getBoolean("confirmed") );
            }
        });

        return order;
    }

    public static Order confirmOrder(Integer id) {
        String query = "UPDATE public.order SET confirmed = true WHERE id = ? RETURNING id";

        Order order = new Order();
        connect(query, (connection, statement) -> {
            statement.setInt(1, id);

            ResultSet rSet = statement.executeQuery();
            connection.commit();

            if(rSet.next()) {
                order.copy(getOrderById(id));
            }
        });

        return order;
    }

    public static Order paidOrder(Integer order_id, Integer user_id) {
        String query = "UPDATE public.order SET paid = true WHERE id = ? AND user_id = ? RETURNING id";

        Order order = new Order();
        connect(query, (connection, statement) -> {
            statement.setInt(1, order_id);
            statement.setInt(2, user_id);

            ResultSet rSet = statement.executeQuery();
            connection.commit();

            if(rSet.next()) {
                order.copy(getOrderById(order_id));
            }
        });

        return order;
    }


    public static Order addOrder(Order order) {
        String query = "INSERT INTO public.order (user_id) VALUES (?) RETURNING id;";

        Order newOrder = new Order();

        connect(query, (connection, statement) -> {
            User user = order.getUser();
            Integer user_id = user.getId();

            statement.setInt(1, user_id);

            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                Integer order_id = rSet.getInt(1);

                String query_ = "INSERT INTO order_product VALUES (?, ?);";

                PreparedStatement statement_ = connection.prepareStatement(query_);
                
                for(Product product : order.getProducts()) {
                    Integer product_id = product.getId();

                    statement_.setInt(1, order_id);
                    statement_.setInt(2, product_id);

                    statement_.addBatch();

                }
                statement_.executeBatch();

                connection.commit();

                newOrder.copy(getOrderById(order_id));
            }
        });

        return newOrder;
    }
}