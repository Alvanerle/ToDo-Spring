package ru.imangali.spring.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.imangali.spring.domain.Task;

import java.util.Collection;
import java.util.Date;

public interface TaskRepo extends CrudRepository<Task, Long> {
    @Query(
            value="SELECT * FROM Task WHERE user_id=:user_id AND type='Assigned' ORDER BY deadline, priority DESC",
            nativeQuery = true
    )
    Collection<Task> assigned_tasks(@Param("user_id") Long user_id);

    @Query(
            value="SELECT * FROM Task WHERE user_id=:user_id AND type='To Do' ORDER BY deadline, priority DESC",
            nativeQuery = true
    )
    Collection<Task> to_do_tasks(@Param("user_id") Long user_id);

    @Query(
            value="SELECT * FROM Task WHERE user_id=:user_id AND type='Finished' ORDER BY deadline, priority DESC",
            nativeQuery = true
    )
    Collection<Task> finished_tasks(@Param("user_id") Long user_id);

    @Transactional
    @Modifying
    @Query(
        value="UPDATE Task SET name=:name, description=:description, deadline=:deadline, priority=:priority, type=:type WHERE id=:id",
        nativeQuery = true
    )
    void update(@Param("name") String name, @Param("description") String description, @Param("deadline") Date deadline, @Param("priority") int priority, @Param("type") String type, @Param("id") Long id);

    @Query(
        value="SELECT * FROM Task where id=:id",
            nativeQuery = true
    )
    Task get(Long id);
}
