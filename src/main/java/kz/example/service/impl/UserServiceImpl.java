package kz.example.service.impl;

import kz.example.repository.UserRepository;
import kz.example.model.User;
import kz.example.dto.UserDto;
import kz.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

	@Override
	public User findUser(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
    public User saveUser(UserDto userDto) {
	    User newUser = new User();
	    newUser.setUsername(userDto.getName());
	    newUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        return userRepository.save(newUser);
    }
}
