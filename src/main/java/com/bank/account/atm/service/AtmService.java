package com.bank.account.atm.service;



import com.bank.account.atm.model.Account;
import com.bank.account.atm.model.Atm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Calendar;
import java.util.UUID;

@Service
public class AtmService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String createAtm(Account account, Atm atm) throws SQLException {
        // Validate that the account exists
        if (!isAccountValid(account.getAccountNumber())) {
            throw new SQLException("Account not found for account number: " + account.getAccountNumber());
        }

        // Generate ATM card details
        String cardNumber = UUID.randomUUID().toString();
        String pin = generatePin();
        String cvv = generateCvv();
        Date expiryDate = generateExpiryDate();

        String query = "INSERT INTO bank.atm (cardNumber, pin, accountNumber, cvv, cardExpiry) VALUES (?, ?, ?, ?, ?)";

        int status = jdbcTemplate.update(query, cardNumber, pin, account.getAccountNumber(), cvv, expiryDate);

        if (status == 1) {
            System.out.println("ATM created successfully with card number: " + cardNumber);
            atm.setCardNumber(cardNumber);
            atm.setPin(pin);
            atm.setCvv(cvv);
            atm.setCardExpiry(expiryDate);
            return cardNumber; // Return the generated card number
        } else {
            throw new SQLException("ATM creation failed for account number: " + account.getAccountNumber());
        }
    }

    private boolean isAccountValid(String accountNumber) {
        String query = "SELECT COUNT(*) FROM bank.account WHERE accountNumber = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, accountNumber) > 0;
    }

    private String generatePin() {
        return String.valueOf((int) (Math.random() * 9000) + 1000); // 4-digit PIN
    }

    private String generateCvv() {
        return String.valueOf((int) (Math.random() * 900) + 100); // 3-digit CVV
    }

    private Date generateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 5); // Add 5 years to the current date
        return new Date(calendar.getTimeInMillis());
    }


    public String getCardNumberByAccount(String accountNumber) throws SQLException {
        String query = "SELECT cardNumber FROM bank.atm WHERE accountNumber = ?";
        String cardNumber = jdbcTemplate.queryForObject(query, String.class, accountNumber);

        if (cardNumber == null) {
            throw new SQLException("ATM card not found for account number: " + accountNumber);
        }

        return cardNumber;
    }
    }







