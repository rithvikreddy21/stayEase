package com.crio.stayEase.services.authService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.crio.stayEase.dto.UserDto;
import com.crio.stayEase.mapper.Mapper;
import com.crio.stayEase.repositoryServices.UserRepositoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepositoryService userRepositoryService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDto userDto = userRepositoryService.findUserByEmail(username);
        return Mapper.mapToUser(userDto);
    }
    
}
