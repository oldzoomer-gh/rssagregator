package ru.gavrilovegor519.rssaggregator.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gavrilovegor519.rssaggregator.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(long id);

    boolean existsByEmail(String email);
}
