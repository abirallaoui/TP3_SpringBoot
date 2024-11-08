package com.tpspring.tp3.controller;

import com.tpspring.tp3.Entity.role;
import com.tpspring.tp3.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //  ajouter un role
    @PostMapping
    public ResponseEntity<role> ajouterRole(@RequestBody role nouveauRole) {
        try {
            // Utilisation du service pour ajouter le role
            role roleAjoute = roleService.ajouterRole(nouveauRole);
            return new ResponseEntity<>(roleAjoute, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // suppression d'un role par ID
    @DeleteMapping("/{roleId}")
    public ResponseEntity<String> deleteRole(@PathVariable Long roleId) {
        try {
            roleService.deleteRole(roleId);
            return ResponseEntity.ok("role supprimé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("role non trouvé");
        }
    }
}
