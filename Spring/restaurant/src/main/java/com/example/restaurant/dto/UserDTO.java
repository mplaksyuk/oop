package com.example.restaurant.dto;

import com.example.restaurant.entity.User;
import com.example.restaurant.entity.enums.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String cash;
    private String salt;
    private Role role;
    private String auth;

    public static UserDTO Convert(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setSalt(user.getSalt());
        dto.setCash(user.getCash());
        dto.setRole(user.getRole());
        dto.setAuth(user.getAuth());
        return dto;
    }
}
