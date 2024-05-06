package com.studyStorm.service;


import com.studyStorm.dto.AddUserRequest;
import com.studyStorm.entity.User;
import com.studyStorm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;




    public String addUser(AddUserRequest addUserRequest) {
        User user = new User();

        if (addUserRequest.firstName() != null && !addUserRequest.firstName().isEmpty()) {
            user.setFirstName(addUserRequest.firstName());

        } else {
            return "First Name is required";
        }

        if (addUserRequest.lastName() != null && !addUserRequest.lastName().isEmpty()) {
            user.setLastName(addUserRequest.lastName());
        } else {
            return "Last Name is required";
        }

        if (addUserRequest.email() != null && !addUserRequest.email().isEmpty()) {
            if (isValidEmail(addUserRequest.email())) {
                user.setEmail(addUserRequest.email());
            } else {
                return "Invalid Email format";
            }
            if (repository.findByEmail(addUserRequest.email()).isPresent()) {
                return "Email already exists";

             }

        } else {
            return "Email is required";
        }

        if (addUserRequest.phoneNumber() != null && !addUserRequest.phoneNumber().isEmpty()) {
            if (isValidPhoneNumber(addUserRequest.phoneNumber())) {
                user.setPhoneNumber(addUserRequest.phoneNumber());
            } else {
                return "Invalid Phone Number format";
            }
        } else {
            return "Phone Number is required";
        }

        if (addUserRequest.password() != null && !addUserRequest.password().isEmpty()) {
            if (addUserRequest.confirmPassword() != null && !addUserRequest.confirmPassword().isEmpty()) {
                if (addUserRequest.password().equals(addUserRequest.confirmPassword())) {
                    user.setPassword(passwordEncoder.encode(addUserRequest.password()));
                } else {
                    return "Password and Confirm Password do not match";
                }
            } else {
                return "Confirm Password is required";
            }
        } else {
            return "Password is required";
        }

        if (addUserRequest.roles() != null && !addUserRequest.roles().isEmpty()) {
            user.setRoles(addUserRequest.roles());
        } else {
            return "Roles is required";
        }

        repository.save(user);
        return "User added successfully";
    }

    // Utility method to validate email address format
    private boolean isValidEmail(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }

    // Utility method to validate phone number format
    private boolean isValidPhoneNumber(String phoneNumber) {
        // For simplicity, let's assume a valid phone number contains only digits and is 10 digits long
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }




    public User findByEmail(String username) {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
