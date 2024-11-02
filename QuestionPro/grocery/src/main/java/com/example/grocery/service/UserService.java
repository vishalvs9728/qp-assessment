package com.example.grocery.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.grocery.dto.JwtRequest;
import com.example.grocery.dto.JwtResponse;
import com.example.grocery.dto.UserDto;
import com.example.grocery.exception.UserAlreadyExist;
import com.example.grocery.jwt.JwtAuthenticationHelper;
import com.example.grocery.model.Role;
import com.example.grocery.model.User;
import com.example.grocery.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtAuthenticationHelper jwtAuthenticationHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	public JwtResponse login(@RequestBody JwtRequest jwtRequest) {

		doAuthendicate(jwtRequest.getUsername(), jwtRequest.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = jwtAuthenticationHelper.generateToken(userDetails);
		JwtResponse response = JwtResponse.builder().jwtToken(token).build();
		return response;
	}

	private void doAuthendicate(String username, String password) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);
		try {
			authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid Username or Password");
		}
	}

	public void register(UserDto userDto) {
		User user = new User();
		User existingUser = userRepository.findByUsername(userDto.getUsername()).orElse(null);
		if(existingUser!=null) {
			throw new UserAlreadyExist("User already exist with username "+userDto.getUsername());
		}
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
		user.setName(userDto.getName());
		user.setPassword(encodedPassword);
		user.setUsername(userDto.getUsername());
		user.setAddress(userDto.getAddress());
		user.setNumber(userDto.getNumber());
		Role role = new Role();
		Set<Role> roles = new HashSet<>();
		role.setRoleName("ROLE_" + userDto.getRole().toUpperCase());
		if (!"ADMIN".equalsIgnoreCase(userDto.getRole()) && !"USER".equalsIgnoreCase(userDto.getRole())) {
			role.setRoleName("ROLE_USER");
		}
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);

	}
}
