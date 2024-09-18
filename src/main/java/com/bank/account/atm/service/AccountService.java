package com.bank.account.atm.service;



import com.bank.account.atm.controller.UserRepository;
import com.bank.account.atm.exception.AccountCreationFailedException;
import com.bank.account.atm.model.Account;
import com.bank.account.atm.model.AccountAddressEntity;
import com.bank.account.atm.model.AccountEntity;
import com.bank.account.atm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.stereotype.Service;


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


    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bankPU");

    public String oneToManyUsingHibernateFromUI(Account account) {

        // Get the EntityManager from the factory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Begin transaction
        entityManager.getTransaction().begin();

        // Map Account DTO to AccountEntity
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountNumber(UUID.randomUUID().toString());
        accountEntity.setName(account.getName());
        accountEntity.setPan(account.getPan());
        accountEntity.setBalance(account.getBalance());
        accountEntity.setMobileNumber(account.getMobileNumber());

        // Map Address DTO to AccountAddressEntity
        AccountAddressEntity accountAddressEntity = new AccountAddressEntity();
        accountAddressEntity.setAddress1(account.getAddress().getAdd1());
        accountAddressEntity.setAddress2(account.getAddress().getAdd2());
        accountAddressEntity.setState(account.getAddress().getState());
        accountAddressEntity.setCity(account.getAddress().getCity());
        accountAddressEntity.setPincode(account.getAddress().getPincode());
        accountAddressEntity.setStatus(1);
        accountAddressEntity.setAccountEntity(accountEntity);

        List<AccountAddressEntity> accountAddressEntities = new ArrayList<>();
        accountAddressEntities.add(accountAddressEntity);
        accountEntity.setAccountAddressEntityList(accountAddressEntities);

        // Persist entity to database
        entityManager.persist(accountEntity);

        // Commit transaction
        entityManager.getTransaction().commit();

        // Close entity manager
        entityManager.close();

        return accountEntity.getAccountNumber();
    }
}
