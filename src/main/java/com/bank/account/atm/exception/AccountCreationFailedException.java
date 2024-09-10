package com.bank.account.atm.exception;

public class AccountCreationFailedException extends  Exception{

      public String message;

    public AccountCreationFailedException(String msg) {
        this.message = msg;
    }
}
