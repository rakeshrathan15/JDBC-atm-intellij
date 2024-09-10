package com.bank.account.atm.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public  static Connection connection;

    public static Connection getConnection(){

        try {

          if(connection==null) {

              System.out.println("getting connectiom");
              Class.forName("com.mysql.cj.jdbc.Driver");
              connection = DriverManager.getConnection("jdbc:mysql://@localhost:3306/bank",
                      "root", "rakesh");
          } else {
              System.out.println("Returning existing connectiom");
          }

        } catch (Exception e){

            System.out.println("Exception ocured "+e);

        }
        return connection;
    }
}
