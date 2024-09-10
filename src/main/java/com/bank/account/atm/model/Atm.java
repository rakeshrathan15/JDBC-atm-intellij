package com.bank.account.atm.model;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
public class Atm {

    private String cardNumber;
    private String pin;
    private String accountNumber;
    private String cvv;
    private Date cardExpiry;

    // Constructor to use with @Builder
    public Atm(String cardNumber, String pin, String accountNumber, String cvv, Date cardExpiry) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.accountNumber = accountNumber;
        this.cvv = cvv;
        this.cardExpiry = cardExpiry;
    }
}
