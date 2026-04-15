package com.fourimpact.module5_taskmanager.Repository;

import com.fourimpact.module5_taskmanager.Entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // ── Derived query methods ──────────────────────────────────────────────
    // Spring reads the method name and generates the SQL automatically.
    // No implementation needed -- Spring does it at runtime.
    List<Task> findByStatus(String status);
    List<Task> findByUserId(Long userId);
    List<Task> findByStatusAndUserId(String status, Long userId);
    List<Task> findByTitleContainingIgnoreCase(String keyword);
    List<Task> findByPriorityOrderByCreatedAtDesc(String priority);

    // ── Paginated query ────────────────────────────────────────────────────
    Page<Task> findByUserId(Long userId, Pageable pageable);

    // ── JOIN FETCH queries (explained in Section 7) ───────────────────────
    // These load related entities in a single SQL query to avoid N+1 problems.
    @Query("SELECT t FROM Task t JOIN FETCH t.user JOIN FETCH t.category")
    List<Task> findAllWithUserAndCategory();

    Page<Task> findByUserIdPaginated(@Param("userId") Long userId, Pageable pageable);

}
