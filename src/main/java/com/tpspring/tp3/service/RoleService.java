package com.tpspring.tp3.service;

import com.tpspring.tp3.Entity.role;
import com.tpspring.tp3.repository.RoleRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // methode pour ajouter un rôle
    public role ajouterRole(role nouveauRole) {
        // Enregistrer le rôle dans la base de données
        return roleRepository.save(nouveauRole);
    }

    public Optional<role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

     // methode pour supprimer un rôle par ID
     public void deleteRole(Long roleId) {
        // Vérification si le rôle existe
        if (roleRepository.existsById(roleId)) {
            roleRepository.deleteById(roleId);  // Suppression du rôle
        } else {
            throw new RuntimeException("Rôle non trouvé");  // Si le rôle n'existe pas
        }
    }
    
}
