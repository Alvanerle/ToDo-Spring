package ru.imangali.spring.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.imangali.spring.models.User;

@Component
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public User find(String username){
        return jdbcTemplate.query("SELECT * FROM Usr WHERE username=?", new Object[]{username}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    public void save(User user){
        jdbcTemplate.update("INSERT INTO Usr (username, password, active) VALUES (?, ?, ?)", user.getUsername(), user.getPassword(), user.isActive());
        jdbcTemplate.update("INSERT INTO Role (username, role) VALUES (?, ?)", user.getUsername(), "USER");
    }
}
