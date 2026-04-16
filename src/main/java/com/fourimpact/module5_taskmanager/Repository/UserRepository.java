// ── UserRepository.java ───────────────────────────────────────────────────
package com.fourimpact.module5_taskmanager.Repository;

import com.fourimpact.module5_taskmanager.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query(value = "SELECT u FROM User u",
            countQuery = "SELECT COUNT(u) FROM User u")
    Page<User> findAllUserPaginated(Pageable pageable);
}