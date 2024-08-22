
package com.agusp.controller;

import com.agusp.base.BaseProgress;
import com.agusp.base.ResponsBody;
import com.agusp.exeption.CustomException;
import com.agusp.model.Users;
import com.agusp.security.JwtTokenProvider;
import com.agusp.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/membership")
public class UserController extends BaseProgress{

    @Autowired
    private ModelMapper modelMapper;
    private UserService service;


//    @PostMapping("/registration")
//    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
//        userService.register(userDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
//    }
    
    @PostMapping("/registration")
    public HttpEntity<Object> register(@RequestBody Users model,
            @RequestParam(defaultValue = "Login", required = false) String flag) {
        try {
            long data = service.checkData(model.getId(), Users.class);
            if (data > 0) {
                throw new CustomException("The data is exist", HttpStatus.BAD_REQUEST);
            }
            service.register(model, flag);
            return new HttpEntity<>(new ResponsBody("Register success"));
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    
    @PostMapping("/login")
    public HttpEntity<Object> login(@RequestBody Users model,
            @RequestParam(defaultValue = "Login", required = false) String flag) {
        try {
            long data = service.checkData(model.getId(), Users.class);
            if (data > 0) {
                throw new CustomException("The data is exist", HttpStatus.BAD_REQUEST);
            }
            service.login(model, flag);
            return new HttpEntity<>(new ResponsBody("Login success"));
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    
    @GetMapping("/profile")
    public HttpEntity<Object> profile(@RequestParam String code) {
        try {
            Object model = service.profile(code, Users.class);
            if (model == null) {
                throw new CustomException("The data doesn't exist", HttpStatus.BAD_REQUEST);
            }

            return new HttpEntity<>(new ResponsBody(model));
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}

