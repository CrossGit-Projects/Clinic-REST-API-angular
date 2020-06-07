package com.example.userapp.repository;
import java.util.Optional;

import com.example.userapp.model.ERole;
import com.example.userapp.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<AppRole, String> {
    Optional<AppRole> findByName(ERole name);

}
