package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;


import com.example.DTO.UserDTO;
import com.example.entity.Users;

import com.example.service.user_services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController

@RequestMapping("/user")

public class user_controller {

    @Autowired

    private user_services service;



    @PostMapping("/registration")

    public ResponseEntity<Users> addUser(@RequestBody Users rs) {

        // Check if a user with the same email already exists

        if (service.isUserExists(rs.getEmail())) {

            return new ResponseEntity("User with the given email already exists", HttpStatus.CONFLICT);

        }

        Users reg = service.addUser(rs);

        

        if (reg != null) {

            return new ResponseEntity("User registered successfully check your mail", HttpStatus.OK);

        } else {

            return new ResponseEntity("User registration failed", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/validate-otp")

    public ResponseEntity<String> validateOtp(@RequestParam String email, @RequestParam String enteredOtp) {

        boolean isValid = service.validateOtp(email, enteredOtp);



        if (isValid) {

            return new ResponseEntity<>("OTP validation successful", HttpStatus.OK);

        } else {

            return new ResponseEntity<>("Invalid OTP", HttpStatus.UNAUTHORIZED);

        }

    }
    
    
    @PostMapping("/resend-otp")
	public ResponseEntity<String> resendOtp(@RequestParam String email) {
    	if (!service.isEmailExists(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email not found in the database");
        }
	    return service.resendOtp(email);
	}
	
	@PostMapping("/initiate-password-reset")
    public ResponseEntity<String> initiatePasswordReset(@RequestParam String email) {
		if (!service.isEmailExists(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email not found in the database");
        }
        return service.initiatePasswordReset(email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String email,
            @RequestParam String resetToken,
            @RequestParam String newPassword) {
    	if (!service.isEmailExists(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email not found in the database");
        }
        return service.resetPassword(email, resetToken, newPassword);
    }



    @GetMapping("/verifyUser/{email}")

    public ResponseEntity<Users> verifyUser(@PathVariable String email){

        Users rs = service.verifyUser(email);

        if(rs != null)

            return new ResponseEntity("User verified", HttpStatus.OK);

        else

            return new ResponseEntity("User Not Found, Please register with us!!!",HttpStatus.NOT_FOUND);

    }

    

//    @PostMapping("/user/login")
//    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password, @RequestParam String role) {
//        Users user = service.loginUser(email, password, role);
//        if (user != null) {
//            return new ResponseEntity<>("User Login successful", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Invalid email, password, or role", HttpStatus.UNAUTHORIZED);
//        }
//    }
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(
        @RequestParam String email,
        @RequestParam String password,
        @RequestParam String role
    ) {
        UserDTO loggedInUser = service.loginUser(email, password, role);
        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    

    
    @GetMapping("/id/{userId}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable int userId) {
        Users user = service.getUserById(userId);
        if (user != null) {
            UserDTO userDTO = convertToDTO(user);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    private UserDTO convertToDTO(Users user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        // Other properties if needed
        return userDTO;
    }
   

    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session
        request.getSession().invalidate();
        // Redirect the user to a login page or any other appropriate page
        return ResponseEntity.ok("User logged out successfully.");
    }
    
    @GetMapping("/details/id/{userId}")
    public ResponseEntity<UserDTO> getUserDetailsById(@PathVariable int userId) {
        UserDTO userDTO = service.getUserDetailsById(userId);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/details/email")
    public ResponseEntity<UserDTO> getUserDetailsByEmail(@RequestParam String email) {
        UserDTO userDTO = service.getUserDetailsByEmail(email);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}









