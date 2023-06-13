package Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Models.User;
import Models.enums.Role;
import abstr.DaoAbstract;

import org.apache.log4j.Logger;

public class UserDao extends DaoAbstract {
    private static final Logger log = Logger.getLogger(UserDao.class);

    UserDao() { super(); }

    public static User getUser(Integer id) {

        String query = "SELECT * FROM public.user WHERE id = ?";

        User user = new User();

        connect(query, (statement) -> {
            statement.setInt(1, id);

            ResultSet rSet = statement.executeQuery();

            if(rSet.next()) {
                user.setId( rSet.getInt("id") );
                user.setName( rSet.getString("name") );
                user.setEmail( rSet.getString("email") );
                user.setCash( rSet.getString("cash") );
                user.setSalt( rSet.getString("salt") );
                user.setRole( Role.valueOf(rSet.getString("role")) );
            }

        });

        return user;
    }

    public static User getUserByEmail(String email) {

        String query = "SELECT * FROM public.user WHERE email = ?";

        User user = new User();

        connect(query, (statement) -> {
            statement.setString(1, email);

            ResultSet rSet = statement.executeQuery();

            if(rSet.next()) {
                user.setId( rSet.getInt("id") );
                user.setName( rSet.getString("name") );
                user.setEmail( rSet.getString("email") );
                user.setCash( rSet.getString("cash") );
                user.setSalt( rSet.getString("salt") );
                user.setRole( Role.valueOf(rSet.getString("role")) );
            }
        });

        return user;
    }

    public static User getUserByEmailAndAuth(String email, String auth) {
        String query = "SELECT * FROM public.user WHERE email = ? AND auth = ?";

        User user = new User();

        connect(query, (statement) -> {
            statement.setString(1, email);
            statement.setString(2, auth);

            ResultSet rSet = statement.executeQuery();

            if(rSet.next()) {
                user.setId( rSet.getInt("id") );
                user.setName( rSet.getString("name") );
                user.setEmail( rSet.getString("email") );
                user.setCash( rSet.getString("cash") );
                user.setSalt( rSet.getString("salt") );
                user.setRole( Role.valueOf(rSet.getString("role")) );
            }
        });

        return user;
    }

    public static List<User> getUsers() {

        String query = "SELECT * FROM public.user";

        List<User> users = new ArrayList<>();

        connect(query, (statement) -> {
            ResultSet rSet = statement.executeQuery();

            while(rSet.next()) {
                Integer id    = rSet.getInt("id");
                String  name  = rSet.getString("name");
                String  email = rSet.getString("email");
                String  cash  = rSet.getString("cash");
                String  salt  = rSet.getString("salt");
                String  auth  = rSet.getString("auth");
                Role    role  = Role.valueOf(rSet.getString("role"));

                users.add(new User(id, name, email, cash, salt, auth, role));
            }
        });

        return users;
    }

    public static void updateUser(User user) {
        String query = "UPDATE public.user SET name = ?, email = ?, cash = ?, salt = ?, role = ? WHERE id = ?";

        connect(query, (statement) -> {
            statement.setString(1, user.getName()  );
            statement.setString(2, user.getEmail() );
            statement.setString(3, user.getCash()  );
            statement.setString(4, user.getSalt()  );
            statement.setString(5, user.getRole().name());

            statement.setInt(1, user.getId());

            if (statement.executeUpdate() <= 0 )
                log.info("Can`t update user");    
        });
    }

    public static User addUser(User user) {

        String query = "INSERT INTO public.user (name, email, cash, salt, auth, role) VALUES (?, ?, ?, ?, ?, ?::role) RETURNING id, name, email, cash, salt, auth, role";

        User newUser = new User();

        connect(query, (statement) -> {
            statement.setString(1, user.getName()  );
            statement.setString(2, user.getEmail() );
            statement.setString(3, user.getCash()  );
            statement.setString(4, user.getSalt()  );
            statement.setString(5, user.getAuth()  );
            statement.setString(6, user.getRole().name());
                
            ResultSet rSet = statement.executeQuery();
            if(rSet.next()) {
                newUser.setId( rSet.getInt("id") );
                newUser.setName( rSet.getString("name") );
                newUser.setEmail( rSet.getString("email") );
                newUser.setCash( rSet.getString("cash") );
                newUser.setSalt( rSet.getString("salt") );
                newUser.setAuth( rSet.getString("auth") );
                newUser.setRole( Role.valueOf(rSet.getString("role")) );
            }
        });

        return newUser;
    }

    
}
