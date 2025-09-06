package com.vayana.projectmanagement.repository;

import com.vayana.projectmanagement.entity.TaskMaster;
import com.vayana.projectmanagement.entity.User;
import com.vayana.projectmanagement.enums.TaskPriority;
import com.vayana.projectmanagement.enums.TaskStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskMasterRepository extends JpaRepository<TaskMaster,Long> {

    @Query("SELECT t FROM TaskMaster t " +
            "WHERE t.id = :id " +
            "AND t.projectMaster.user.id = :userId")
    Optional<TaskMaster> findByIdAndUser(@Param("id") Long id, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE From TaskMaster t " +
            "WHERE t.id = :id " +
            "AND t.projectMaster.user.id = :userId")
    int deleteTask(@Param("id") Long id,@Param("userId") Long userId);

    @Query("SELECT t FROM TaskMaster t " +
            "WHERE t.projectMaster.user.id = :userId " +
            "AND ( :title IS NULL OR LOWER(t.title) LIKE (CONCAT('%',LOWER(:title),'%'))) " +
            "AND ( :status IS NULL OR t.status = :status) " +
            "AND ( :priority IS NULL OR t.priority = :priority) " +
            "AND ( :dueDate IS NULL OR :dueDate = t.dueDate) " +
            "AND ( :listIds IS NULL OR t.projectMaster.id IN :listIds )")
    Page<TaskMaster> searchTaskFilter(@Param("userId")Long userId, @Param("title") String title, @Param("status") TaskStatus status, @Param("priority")TaskPriority priority, @Param("dueDate")LocalDate dueDate, @Param("listIds")List<Long> listIds, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE From TaskMaster t " +
            "WHERE t.projectMaster.id = :projectId " +
            "AND t.projectMaster.user.id = :userId")
    int deleteByProject(@Param("projectId") Long projectId,@Param("userId") Long userId);
}
