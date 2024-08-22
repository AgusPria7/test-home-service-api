
package com.agusp.repo;

import com.agusp.model.Users;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void register(Users users) {
        String sql = "INSERT INTO users (email, first_name, last_name, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, users.getEmail(),users.getFirst_name(),users.getLast_name(), users.getPassword());
    }
    
    public void save(Users users) {
        String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, users.getEmail(), users.getPassword());
    }

    public BigDecimal getBalance(String email) {
        String sql = "SELECT balance FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, BigDecimal.class);
    }

    public void updateBalance(String email, BigDecimal newBalance) {
        String sql = "UPDATE users SET balance = ? WHERE email = ?";
        jdbcTemplate.update(sql, newBalance, email);
    }

    // Additional methods for transactions
}

