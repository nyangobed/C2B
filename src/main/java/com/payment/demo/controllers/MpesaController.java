package com.payment.demo.controllers;


import com.payment.demo.entities.Users;
import com.payment.demo.services.mpesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
public class MpesaController {

    @Autowired
    private mpesaService service;

    @RequestMapping(value = "/generateToken", method = RequestMethod.POST)
    public ResponseEntity <Object> generateAcessToken(){
        Map<String,Object> response= service.simulateC2B();
        return new ResponseEntity (response ,HttpStatus.OK );

    }

    @RequestMapping(value = "/simulateC2B", method = RequestMethod.POST)
    public ResponseEntity <Object> simulateC2B(){
        Map<String,Object> response= service.simulateC2B();
        return new ResponseEntity (response ,HttpStatus.OK );

    }
    @RequestMapping(value = "/users")
    public ResponseEntity <Object> getUser(@RequestParam long userId){

        Users user= new Users();
        user.setId(userId);
        return ResponseEntity.ok(user);

    }


}
