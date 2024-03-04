package com.tomas.practical.service;

import com.tomas.practical.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Boolean login(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";

        int count = jdbcTemplate.queryForObject(sql, new Object[]{username, password}, Integer.class);

        return count > 0;
    }

    public Boolean createUser(String username, String password) {
        // Check if the username already exists
        if (usernameExists(username)) {
            return false;  // Username already taken
        }

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        int affectedRows = jdbcTemplate.update(sql, username, password);

        return affectedRows > 0;
    }

    private boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count > 0;
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
}