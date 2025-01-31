package org.example.User;

import org.example.User.UserEnum;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastname;
    private UserEnum role;
    private String profileImage;
    private Boolean active;
}
