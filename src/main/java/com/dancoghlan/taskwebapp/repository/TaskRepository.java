package com.dancoghlan.taskwebapp.repository;

import com.dancoghlan.taskwebapp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    @Query(value = "SELECT * FROM Task t where t.user_id = :userId", nativeQuery = true)
    List<Task> findAllByUserId(long userId);
}
