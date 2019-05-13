package com.payment.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.demo.entities.Users;
import com.payment.demo.models.LoginRequest;
import com.payment.demo.models.RegisterRequest;
import com.payment.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

// registration of users
    @Override
    public Map<String, Object> register(RegisterRequest request) {
        Map<String, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        Users user = new Users();
        user.setFirstname(request.getFirstname());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setCreatedOn(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder().encode(request.getPassword()));

        user.setEmail(request.getEmail());
        userRepository.save(user);
        map.put("status", "00");
        map.put("message", "Registration Sucessful");
        return map;
    }
//    @Override
//    public Map<String, Object> all(RegisterRequest request) {
//        Map<String, Object> map = new HashMap<>();
//        Optional<Users> all= userRepository.findAllByEmailOrderByIdAsc(request.getEmail());
//        if (!all.isPresent()) {
//            map.put("status", "01");
//            map.put("message", "login sucessful");
//            return map;
//        }
//        Users user2 = all.get();
//        map.put("firstname", user2.getFirstname());
//        map.put("lastname", user2.getLastname());
//        map.put("email", user2.getEmail());
//        map.put("createdon", user2.getCreatedOn());
//
//        return map;
//    }

    // encrypt password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // log in of registered users
    @Override

    public Map<String, Object> login(LoginRequest request) {
        Map<String, Object> map = new HashMap<>();
        Optional<Users> ouser = userRepository.findUsersByUsernameAndPassword(request.getUsername(), passwordEncoder().encode(request.getPassword()));
        if (!ouser.isPresent()) {
            map.put("status", "01");
            map.put("message", "login sucessful");
            return map;
        }
        Users user = ouser.get();
        map.put("firstname", user.getFirstname());
        map.put("lastname", user.getLastname());
        map.put("email", user.getEmail());
        map.put("createdon", user.getCreatedOn());

        return map;
    }




}

