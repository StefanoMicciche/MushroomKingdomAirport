package org.example.User;

import lombok.RequiredArgsConstructor;
import org.example.User.Exceptions.UserNotFoundException;
import org.example.User.Exceptions.UserNotFoundWithIdException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::responseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id){
        return userRepository.findById(id)
                .map(userMapper::responseDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail())){
            throw new IllegalArgumentException("Username already exists");
        }

        UserEntity user = userMapper.userEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        return userMapper.responseDTO(userRepository.save(user));
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundWithIdException("User not found with id: " + id));

        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());

        if (!user.getEmail().equals(userRequestDTO.getEmail()) &&
                userRepository.existsByEmail(userRequestDTO.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }
        return userMapper.responseDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.setActive(false);
        userRepository.save(user);
    }
}
