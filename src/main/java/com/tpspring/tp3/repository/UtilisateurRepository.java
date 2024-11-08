package com.tpspring.tp3.repository;

import com.tpspring.tp3.Entity.role;
import com.tpspring.tp3.Entity.utilisateur;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<utilisateur, Long> {

    Optional<utilisateur> findByEmail(String email);

    List<utilisateur> findByRole(role role);  
}
