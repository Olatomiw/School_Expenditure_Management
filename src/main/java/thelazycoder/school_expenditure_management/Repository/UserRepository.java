package thelazycoder.school_expenditure_management.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thelazycoder.school_expenditure_management.Model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query("""
        SELECT u FROM User u
        LEFT JOIN FETCH u.department
        LEFT JOIN FETCH u.requestedExpenditures
        WHERE u.id = :id
""")
    Optional<User> findById(@Param("id") UUID id);
}
