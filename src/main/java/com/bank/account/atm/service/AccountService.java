package com.bank.account.atm.service;



import com.bank.account.atm.controller.UserRepository;
import com.bank.account.atm.exception.AccountCreationFailedException;
import com.bank.account.atm.model.Account;
import com.bank.account.atm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;


@Service
@Transactional
public class AccountService {

    public String createAccount(Account account) throws AccountCreationFailedException {
                  String accountNumber=null;

        try {

            Connection connection = DBConnection.getConnection();
            Statement stmt = connection.createStatement();
             accountNumber= UUID.randomUUID().toString();


            String query="insert into bank.account values('"+
                    accountNumber+"'"+","+

            "'"+account.getName()+"'"+","+
            "'"+account.getPan()+"'"+","+
            "'"+account.getMobileNumber()+"'"+","
           + account.getBalance()+")";


            //    insert into bank.account values('1234','rakesh','grc12345','7013776567',20000);

          int status=  stmt.executeUpdate(query);
          if(status==1){

              System.out.println("Account is created "+accountNumber);
          }else {
              throw new AccountCreationFailedException("Account creation is failed"+account.getPan());
          }
        }
        catch (SQLException e){
            System.out.println("Exception ocurred in sql"+e);
        }
        catch (AccountCreationFailedException e){
            System.out.println("Exception ocurred"+e);
            throw e;
        }
        return accountNumber;
    }

    @Autowired
    private UserRepository userRepository;

    public boolean register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return false; // Username already exists
        }
        return userRepository.save(user);
    }

    public boolean validateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}
