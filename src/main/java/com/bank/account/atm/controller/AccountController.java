package com.bank.account.atm.controller;



import com.bank.account.atm.exception.AccountCreationFailedException;
import com.bank.account.atm.model.Account;
import com.bank.account.atm.model.User;
import com.bank.account.atm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin("*")
@RestController
public class AccountController {


    @PostMapping(value="/api/createAccount",
      consumes = "application/json",
    produces = "application/json")
    public Account getAccountNumber(@RequestBody Account account)
            throws AccountCreationFailedException {

        AccountService accountService = new AccountService();
        String accountNumber =accountService.createAccount(account);
        account.setAccountNumber(accountNumber);
        return  account;
    }



    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        boolean isRegistered = accountService.register(user);
        if (isRegistered) {
            return ResponseEntity.ok("Registration successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        boolean isValidUser = accountService.validateUser(user.getUsername(), user.getPassword());
        if (isValidUser) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
