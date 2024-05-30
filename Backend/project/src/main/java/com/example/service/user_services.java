package com.example.service;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

import com.example.DTO.UserDTO;
import com.example.entity.Users;

//import com.example.repo.TeamRepo;

import com.example.repo.user_repo;


@Service
public class user_services {
    @Autowired
    private user_repo repo;

//    @Autowired
//    private TeamRepo teamRepo;

    @Autowired
    private JavaMailSender javaMailSender;
    
    public Users createUser(Users user) {
        return repo.save(user);
    }

    public Users getUserById(int userId) {
        return repo.findById(userId).orElse(null);
    }


    public Users addUser(Users rs) {
        if (isUserExists(rs.getEmail())) {
            return null;
        }

        // Generate and set OTP
        String generatedOtp = rs.generateOtp();
        rs.setOtp(generatedOtp);
        String generatedUsername = rs.generateUsername();
        rs.setUsername(generatedUsername);
        Users savedUser = repo.saveAndFlush(rs);

//      sendRegistrationEmail(savedUser);
        sendOtpEmail(savedUser);
        return savedUser;
    }
    
    public UserDTO getUserDetailsById(int userId) {
        Optional<Users> userOptional = repo.findById(userId);
        return userOptional.map(this::convertToDTO).orElse(null);
    }

    public UserDTO getUserDetailsByEmail(String email) {
        Users user = repo.findByEmail(email);
        return user != null ? convertToDTO(user) : null;
    }

   



//  private void sendRegistrationEmail(user user) {

//      SimpleMailMessage message = new SimpleMailMessage();

//      message.setTo(user.getEmail());

//      message.setSubject("Registration Successful");

//      message.setText("Dear " + user.getFirstName() + ",\n\n" +"OTP: " + user.getOtp()+ ",\n\n"+ "Thank you for registering with us!\n\n"

//              + "Your username: " + user.getUsername() + "\n" + "Your password: " + user.getPassword() + "\n\n"

//              + "Best regards,\nThe Team");

//

//      javaMailSender.send(message);

//

//  }

    
    	private void sendOtpEmail(Users user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("OTP Verification");
        message.setText("Dear " + user.getFirstName() + ",\n\n" + "Your OTP for email verification: " + user.getOtp() + "\n\n"
                + "Please enter this OTP to complete your registration.\n\n" + "Best regards,\nThe Team");
        javaMailSender.send(message);
    }

    private void sendLoginCredentials(Users user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Login Credentials");
        message.setText("Dear " + user.getFirstName() + ",\n\n"
                + "Your login credentials:\n\n"
                + "Username: " + user.getUsername() + "\n"
                + "Password: " + user.getPassword() + "\n\n"
                + "Thank you for registering with us!\n\n"
                + "Best regards,\nThe Team");
        javaMailSender.send(message);
    }

    public boolean isUserExists(String email) {
        Optional<Users> userOptional = Optional.ofNullable(repo.findByEmail(email));
        return userOptional.isPresent();
    }
    public boolean isEmailExists(String email) {
        return repo.existsByEmail(email);
    }

    public Users verifyUser(String email) {
        Optional<Users> op = Optional.ofNullable(repo.findByEmail(email));
        if (op.isPresent())
            return op.get();
        else
            return null;
    }

//    public Users loginUser(String email, String password, String role) {
//        Users user = repo.findByEmail(email);
//        if (user != null && user.getPassword().equals(password) && user.getRole().equals(role)) {
//            return user;
//        }
//        return null;
//    }
    public UserDTO loginUser(String email, String password, String role) {
        Users user = repo.findByEmail(email);
        if (user != null && user.getPassword().equals(password) && user.getRole().equals(role)) {
            return convertToDTO(user);
        }
        return null;
    }

    public UserDTO convertToDTO(Users user) {
        UserDTO userDTO = new UserDTO();
        // Populate the DTO with user details
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setLastName(user.getLastName());
        userDTO.setRole(user.getRole());
        userDTO.setFirstName(user.getFirstName());
        // Set other user details as needed

        return userDTO;
    }
   




//    public boolean validateOtp(String email, String enteredOtp) {
//        Users user = repo.findByEmail(email);
//        if (user != null && user.getOtp() != null && user.getOtp().equals(enteredOtp)) {
////            user.setOtp(null);
//            repo.save(user);
//            sendLoginCredentials(user);
//            return true;
//        }
//        return false;
//    }
    
    public boolean validateOtp(String email, String enteredOtp) {
        Users user = repo.findByEmail(email);
        if (user != null && user.getOtp() != null && user.getOtp().equals(enteredOtp)) {
            // user.setOtp(null); // You might want to uncomment this line if you're clearing OTP after validation
            repo.save(user);
            sendLoginCredentials(user);
            return true;
        }
        return false;
    }
    
    public ResponseEntity<String> resendOtp(String email) {
	    Users user = repo.findByEmail(email);

	    if (user != null) {
	        // Generate a new OTP
	        String newOtp = user.generateOtp();
	        user.setOtp(newOtp);

	        // Update the OTP in the database
	        repo.save(user);

	        // Send the new OTP via email
	        sendOtpEmail(user);

	        return new ResponseEntity("OTP resent successfully", HttpStatus.OK);
	    } else {
	        return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
	    }
	}
    public ResponseEntity<String> initiatePasswordReset(String email) {
        Users user = repo.findByEmail(email);

        if (user != null) {
            user.generateResetToken();
            repo.save(user);

            // Send the reset token via email
            sendResetTokenEmail(user);

            return new ResponseEntity<>("Password reset initiated. Check your email for further instructions.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> resetPassword(String email, String resetToken, String newPassword) {
        boolean isValidToken = validateResetToken(email, resetToken);

        if (isValidToken) {
            // Update the user's password
            Users rs = repo.findByEmail(email);
            rs.setPassword(newPassword);
            repo.save(rs);

            // Send an email or notification confirming the password reset
            sendPasswordResetConfirmationEmail(rs);

            return new ResponseEntity<>("Password reset successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid reset token", HttpStatus.UNAUTHORIZED);
        }
    }

    private void sendResetTokenEmail(Users user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password Reset OTP");
        message.setText("Dear " + user.getFirstName() + ",\n\n" +
                "Your OTP for password reset: " + user.getResetToken() + "\n\n" +
                "Please enter this OTP to reset your password.\n\n" +
                "Best regards,\nThe Team");

        javaMailSender.send(message);
    }

    private void sendPasswordResetConfirmationEmail(Users user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password Reset Confirmation");
        message.setText("Dear " + user.getFirstName() + ",\n\n" +
                "Your password has been successfully reset.\n\n" +
                "Your login credentials:\n\n" 
                + "Username: " + user.getUsername() + "\n"
	            + "Password: " + user.getPassword() + "\n\n"
	            + "Thank you for registering with us!\n\n"+
                "If you did not request this change, please contact us immediately.\n\n" +
                "Best regards,\nThe Team");

        javaMailSender.send(message);
    }

    private boolean validateResetToken(String email, String enteredToken) {
        Users user = repo.findByEmail(email);

        return user != null && user.getResetToken() != null && user.getResetToken().equals(enteredToken);
    }
    
}

