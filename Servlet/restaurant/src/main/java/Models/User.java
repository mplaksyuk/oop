package Models;

import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.annotation.JsonInclude;

import Models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class User {
    private Integer id;
    private String name, email, cash, salt, auth;
    private Role role;

    public User(String name, String email) {
        this.name  = name;
        this.email = email;
        this.cash  = null;
        this.salt  = null;
        this.role  = Role.USER;
    }

    public User(String name, String email, String password) {
        String salt = BCrypt.gensalt();

        this.name  = name;
        this.email = email;
        this.cash  = BCrypt.hashpw(password, salt);
        this.salt  = salt;
        this.role  = Role.USER;
    }

    public User(String name, String email, String password, String salt) {
        this.name  = name;
        this.email = email;
        this.cash  = BCrypt.hashpw(password, salt);
        this.salt  = salt;
        this.role  = Role.USER;
    }

    // public User(String name, String email, String password, Role role) {
    //     String salt = BCrypt.gensalt();

    //     this.name  = name;
    //     this.email = email;
    //     this.cash  = BCrypt.hashpw(password, salt);
    //     this.salt  = salt;
    //     this.role  = role;
    // }

    public User(String name, String email, String password, String salt, Role role) {
        this.name  = name;
        this.email = email;
        this.cash  = BCrypt.hashpw(password, salt);
        this.salt  = salt;
        this.role  = role;
    }

    public Boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.cash);
    }

    public void copy(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.cash = user.getCash();
        this.salt = user.getSalt();
        this.role = user.getRole();
    }
}
