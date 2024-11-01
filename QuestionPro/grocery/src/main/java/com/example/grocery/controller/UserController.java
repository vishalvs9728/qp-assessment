package com.example.grocery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.grocery.dto.JwtRequest;
import com.example.grocery.dto.JwtResponse;
import com.example.grocery.dto.UserDto;
import com.example.grocery.service.UserService;



@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
		return new ResponseEntity<JwtResponse>(userService.login(jwtRequest), HttpStatus.OK);

	}

	@PostMapping("/register")
	public void register(@RequestBody UserDto user) {
		userService.register(user);
	}
}
