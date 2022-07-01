package kz.example.service;

import kz.example.model.User;
import kz.example.dto.UserDto;

public interface UserAuthService {

    User saveUser(UserDto user);

    User findUser(String username);

    Boolean deleteUser(Long userId);
}
