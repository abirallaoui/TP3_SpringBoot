package com.tpspring.tp3.repository;

import com.tpspring.tp3.Entity.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<role, Long> {
    Optional<role> findByNom(String nom);

}
