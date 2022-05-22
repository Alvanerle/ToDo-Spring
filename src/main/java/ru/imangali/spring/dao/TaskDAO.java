package ru.imangali.spring.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.imangali.spring.models.Task;
import ru.imangali.spring.models.User;


import java.util.List;


@Component
public class TaskDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public User find(String username){
        return jdbcTemplate.query("SELECT * FROM Usr WHERE username=?", new Object[]{username}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return find(username);
    }

    public List<Task> index() {
        User user = getCurrentUser();
        return jdbcTemplate.query("SELECT * FROM Task WHERE user_id=? ORDER BY priority, deadline", new Object[]{user.getUser_id()}, new BeanPropertyRowMapper<>(Task.class));
    }

    public Task show(int id){
        User user = getCurrentUser();
        return jdbcTemplate.query("SELECT * FROM Task WHERE id=? AND user_id=?", new Object[]{id, user.getUser_id()}, new BeanPropertyRowMapper<>(Task.class))
                .stream().findAny().orElse(null);
    }

    public void save(Task task){
        User user = getCurrentUser();
        jdbcTemplate.update("INSERT INTO Task (name, description, deadline, priority, user_id) VALUES (?, ?, ?, ?, ?)", task.getName(), task.getDescription(), task.getDeadline(), task.getPriority(), user.getUser_id());
    }

    public void update(int id, Task updatedTask){
        jdbcTemplate.update("UPDATE Task SET name=?, description=?, deadline=?, priority=? WHERE id=?",
                updatedTask.getName(), updatedTask.getDescription(), updatedTask.getDeadline(), updatedTask.getPriority(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Task WHERE id=?", id);
    }
}
