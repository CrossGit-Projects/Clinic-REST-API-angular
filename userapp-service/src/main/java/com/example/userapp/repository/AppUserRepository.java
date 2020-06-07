package com.example.userapp.repository;

import com.example.userapp.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<AppUser> findById(Long appUserId);

    @Modifying
    @Query("UPDATE AppUser u SET u.enabled = :activeParam WHERE u.activationCode = :activationCode")
    void updateActivation(@Param("activeParam") boolean activeParam, @Param("activationCode") String activationCode);

}
