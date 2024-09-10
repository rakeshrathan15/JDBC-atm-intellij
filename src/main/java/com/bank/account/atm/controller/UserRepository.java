package com.bank.account.atm.controller;

import com.bank.account.atm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean save(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        int result = jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
        System.out.println("Save result: " + result);
        return result > 0;
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) ->
                new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }
}
