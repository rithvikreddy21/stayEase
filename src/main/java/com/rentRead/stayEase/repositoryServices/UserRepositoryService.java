package com.crio.stayEase.repositoryServices;

import com.crio.stayEase.dto.UserDto;
import com.crio.stayEase.entities.enums.Role;

public interface UserRepositoryService {

    UserDto registerUser(String firstName, String lastName, String email, String password, Role role);

    UserDto findUserByEmail(String email);
}
