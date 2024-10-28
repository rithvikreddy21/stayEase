package com.crio.stayEase.services;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crio.stayEase.dto.UserDto;
import com.crio.stayEase.entities.enums.Role;
import com.crio.stayEase.exceptions.InvalidCredentialsException;
import com.crio.stayEase.exceptions.InvalidRoleException;
import com.crio.stayEase.exchanges.LoginUserRequest;
import com.crio.stayEase.exchanges.LoginUserResponse;
import com.crio.stayEase.exchanges.RegisterUserRequest;
import com.crio.stayEase.exchanges.RegisterUserResponse;
import com.crio.stayEase.mapper.Mapper;
import com.crio.stayEase.repositoryServices.UserRepositoryService;
import com.crio.stayEase.services.authService.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryService userRepositoryService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationProvider authenticationProvider;

    private final JwtService jwtService;

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {
        String firstName = registerUserRequest.getFirstName();
        String lastName = registerUserRequest.getLastName();
        String email = registerUserRequest.getEmail();
        String password = getEncryptedPassword(registerUserRequest.getPassword());
        Role role = mapToRole(registerUserRequest.getRole());

        UserDto userDto = userRepositoryService.registerUser(firstName, lastName, email, password, role);
        String jwtToken = jwtService.generateToken(Mapper.mapToUser(userDto));

        RegisterUserResponse registerUserResponse = new RegisterUserResponse(userDto, jwtToken);
        return registerUserResponse;
    }

    private String getEncryptedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private Role mapToRole(String role) {
        try {
            return Role.valueOf(role);
        } catch (Exception e) {
            throw new InvalidRoleException("Please provide a valid role");
        }
    }

    @Override
    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest) {
        Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(loginUserRequest.getEmail(), loginUserRequest.getPassword());
        Authentication authenticationToken = authenticationProvider.authenticate(authenticationRequest);

        if(!authenticationToken.isAuthenticated())
            throw new InvalidCredentialsException("Invalid email or password");

        UserDto userDto = userRepositoryService.findUserByEmail(loginUserRequest.getEmail());
        String jwtToken = jwtService.generateToken(Mapper.mapToUser(userDto));
        LoginUserResponse loginUserResponse = new LoginUserResponse(jwtToken);
        return loginUserResponse;
    }
    
}
