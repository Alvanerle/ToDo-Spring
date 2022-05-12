package ru.imangali.spring.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.imangali.spring.models.Task;


import java.util.List;


@Component
public class TaskDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Task> index() {
        return jdbcTemplate.query("SELECT * FROM Task ORDER BY priority", new BeanPropertyRowMapper<>(Task.class));
    }

    public Task show(int id){
        return jdbcTemplate.query("SELECT * FROM Task WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Task.class))
                .stream().findAny().orElse(null);
    }

    public void save(Task task){
        jdbcTemplate.update("INSERT INTO Task (name, description, deadline, priority) VALUES (?, ?, ?, ?)", task.getName(), task.getDescription(), task.getDeadline(), task.getPriority());
    }

    public void update(int id, Task updatedTask){
        jdbcTemplate.update("UPDATE Task SET name=?, description=?, deadline=?, priority=? WHERE id=?",
                updatedTask.getName(), updatedTask.getDescription(), updatedTask.getDeadline(), updatedTask.getPriority(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Task WHERE id=?", id);
    }
}
