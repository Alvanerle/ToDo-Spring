package ru.imangali.spring.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.imangali.spring.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
