
package com.agusp.controller;

import com.agusp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import com.agusp.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private UserService userService;

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(@RequestParam String username) {
        BigDecimal balance = userService.getBalance(username);
        return ResponseEntity.ok(balance);
    }
    
    @PostMapping("/topup")
    public ResponseEntity<String> topUp(@RequestParam String username, @RequestParam BigDecimal amount) {
        userService.topUp(username, amount);
        return ResponseEntity.ok("Top-up successful");
    }
    
    @PostMapping("/transaction")
    public ResponseEntity<String> performTransaction(@RequestBody Transaction model) {
        userService.performTransaction(model);
        return ResponseEntity.ok("Transaction successful");
    }

}

