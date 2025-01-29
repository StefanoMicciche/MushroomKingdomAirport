package org.example.User;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity userEntity(UserRequestDTO requestDTO){
        if (requestDTO == null){
            return null;
        }
        return UserEntity.builder()
                .username(requestDTO.getUsername())
                .email(requestDTO.getEmail())
                .password(requestDTO.getPassword())
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .role(UserEnum.ROLE_USER)
                .active(true)
                .build();
    }

    public UserResponseDTO responseDTO(UserEntity userEntity){
        if (userEntity == null){
            return null;
        }
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userEntity.getId());
        userResponseDTO.setUsername(userEntity.getUsername());
        userResponseDTO.setEmail(userResponseDTO.getEmail());
        userResponseDTO.setFirstName(userResponseDTO.getFirstName());
        userResponseDTO.setLastname(userResponseDTO.getLastname());
        userResponseDTO.setRole(userEntity.getRole());
        userResponseDTO.setProfileImage(userEntity.getProfileImage());
        userResponseDTO.setActive(userEntity.getActive());

        return userResponseDTO;
    }
}
