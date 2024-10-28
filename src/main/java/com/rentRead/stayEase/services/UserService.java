package com.crio.stayEase.services;

import com.crio.stayEase.exchanges.LoginUserRequest;
import com.crio.stayEase.exchanges.LoginUserResponse;
import com.crio.stayEase.exchanges.RegisterUserRequest;
import com.crio.stayEase.exchanges.RegisterUserResponse;

public interface UserService {
    
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);

    LoginUserResponse loginUser(LoginUserRequest loginUserRequest);
}
