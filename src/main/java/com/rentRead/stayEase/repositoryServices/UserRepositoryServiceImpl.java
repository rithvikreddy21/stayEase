package com.crio.stayEase.repositoryServices;

import org.springframework.stereotype.Service;

import com.crio.stayEase.dto.UserDto;
import com.crio.stayEase.entities.User;
import com.crio.stayEase.entities.enums.Role;
import com.crio.stayEase.exceptions.UserNotFoundException;
import com.crio.stayEase.mapper.Mapper;
import com.crio.stayEase.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository userRepository;

    @Override
    public UserDto registerUser(String firstName, String lastName, String email, String password, Role role) {
        User user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .password(password)
                    .role(role)
                    .build();
                    
        User savedUser = userRepository.save(user);
        return Mapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        String message = "Could not find user with email: " + email;
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(message));
        return Mapper.mapToUserDto(user);
    }
    
}
