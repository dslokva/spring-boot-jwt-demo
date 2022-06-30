package kz.example.service;

import kz.example.model.User;
import kz.example.dto.UserDto;

import java.util.List;

public interface UserService {

    User saveUser(UserDto user);

    User findUser(String username);

}
