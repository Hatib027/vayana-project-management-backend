package com.vayana.projectmanagement.repository;

import com.vayana.projectmanagement.entity.ProjectMaster;
import com.vayana.projectmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectMasterRepository extends JpaRepository<ProjectMaster,Long> {

    boolean existsByUserAndName(User user, String name);
    Optional<ProjectMaster> findByUserAndId(User user,Long id);

    Optional<ProjectMaster> findByIdAndUser(Long id,User user);

    boolean existsByUserAndNameAndIdNot(User user, String name,Long id);


    @Query("SELECT p FROM ProjectMaster p " +
            "WHERE p.user.id = :userId " +
            "AND (:name IS NULL OR Lower(p.name) LIKE LOWER(CONCAT('%', :name,'%')))")
    Page<ProjectMaster> searchProjects (@Param("userId") Long userId, @Param("name") String name, Pageable pageable);

    int deleteByIdAndUser(Long id,User user);

}
