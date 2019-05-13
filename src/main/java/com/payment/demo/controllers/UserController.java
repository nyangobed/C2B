package com.payment.demo.controllers;

import com.payment.demo.models.LoginRequest;
import com.payment.demo.models.RegisterRequest;
import com.payment.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity <Object> register(@RequestBody @Valid RegisterRequest request){
        Map<String,Object> response= new HashMap<>();

        response = service.register(request);
        return new ResponseEntity (response ,HttpStatus.OK );

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity <Object> login(@RequestBody @Valid LoginRequest request){
        Map<String,Object> response= new HashMap<>();
        response = service.login(request);
        return new ResponseEntity (response ,HttpStatus.OK );

    }


//    @RequestMapping(value = "/all", method = RequestMethod.POST)
//    public ResponseEntity <Object> all(@RequestBody @Valid RegisterRequest request){
//        Map<String,Object> response= new HashMap<>();
//        response = service.all(request);
//        return new ResponseEntity (response ,HttpStatus.OK );
//
//    }
}
