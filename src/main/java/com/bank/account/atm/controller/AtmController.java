package com.bank.account.atm.controller;



import com.bank.account.atm.model.Account;
import com.bank.account.atm.model.Atm;
import com.bank.account.atm.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@CrossOrigin("*")
public class AtmController {
    @Autowired
    private AtmService atmService;
    @PostMapping(value="/api/createAtm", consumes = "application/json", produces = "application/json")
    public Atm createAtmCard(@RequestBody Account account) throws SQLException {
       // AtmService atmService = new AtmService();
        Atm atm = new Atm(); // Create a new Atm object to hold the generated data
        atmService.createAtm(account, atm); // Pass the account and atm objects to the service
        return atm;
    }

    @GetMapping(value="/api/getCardNumber/{accountNumber}", produces = "application/json")
    public Atm getCardNumber(@PathVariable String accountNumber) throws SQLException {
        AtmService atmService = new AtmService();
        Atm atm = new Atm();
        atm.setAccountNumber(accountNumber);
        String cardNumber = atmService.getCardNumberByAccount(accountNumber);
        atm.setCardNumber(cardNumber);
        return atm;
    }
}
