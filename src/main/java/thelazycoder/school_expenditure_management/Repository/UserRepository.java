package thelazycoder.school_expenditure_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelazycoder.school_expenditure_management.Model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
