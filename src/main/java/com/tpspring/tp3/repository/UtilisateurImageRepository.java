package com.tpspring.tp3.repository;

import com.tpspring.tp3.Entity.utilisateurImage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurImageRepository extends JpaRepository<utilisateurImage, Long> {
    Optional<utilisateurImage> findByUtilisateur_Id(Long utilisateurId);

}
