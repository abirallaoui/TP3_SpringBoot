package com.tpspring.tp3.controller;

import com.tpspring.tp3.Entity.role;
import com.tpspring.tp3.Entity.utilisateur;
import com.tpspring.tp3.Entity.utilisateurImage;
import com.tpspring.tp3.service.RoleService;
import com.tpspring.tp3.service.UtilisateurImageService;
import com.tpspring.tp3.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {


    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private RoleService roleService;

    //  récupérer tous les Utilisateurs
    @GetMapping
    public ResponseEntity<List<utilisateur>> recupererTousLesUtilisateurs() {
        try {
            // Appel du service pour récupérer tous les utilisateurs
            List<utilisateur> utilisateurs = utilisateurService.recupererTousLesUtilisateurs();
            // Retourner les utilisateurs avec un statut OK
            return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Si une exception se produit, renvoyer une erreur interne
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



  


    // Méthode pour créer un nouvel Utilisateur
    @PostMapping("/create")
    public ResponseEntity<utilisateur> createUtilisateur(@RequestBody utilisateur utilisateur) {
        try {
            utilisateur nouvelUtilisateur = utilisateurService.creerUtilisateur(
                utilisateur.getEmail(),
                utilisateur.getNom(),
                utilisateur.getRole().getNom()
            );
            return ResponseEntity.ok(nouvelUtilisateur);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    
     // Récupérer un Utilisateur par son ID
     @GetMapping("/{id}")
     public ResponseEntity<utilisateur> getUtilisateurById(@PathVariable Long id) {
         try {
             utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
             if (utilisateur == null) {
                 // Si l'utilisateur n'est pas trouvé, retourner une erreur 404
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
             }
             return ResponseEntity.ok(utilisateur);
         } catch (Exception e) {
             // Log l'erreur pour la détection des problèmes
             e.printStackTrace();
             utilisateur erreurUtilisateur = new utilisateur();
             erreurUtilisateur.setNom("Erreur: " + e.getMessage());  // Ajouter un message d'erreur
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erreurUtilisateur);
         }
    }




    @PutMapping("/{utilisateurId}/role/{roleId}")
public ResponseEntity<String> assignRoleToUtilisateur(
        @PathVariable Long utilisateurId,
        @PathVariable Long roleId) {
    try {
        // Récupérer l'utilisateur par son ID
        Optional<utilisateur> utilisateurOptional = Optional.of(utilisateurService.getUtilisateurById(utilisateurId));
        if (!utilisateurOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
        }

        // Récupérer le rôle par son ID
        Optional<role> roleOptional = roleService.getRoleById(roleId);
        if (!roleOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rôle non trouvé.");
        }

        // Assigner le rôle à l'utilisateur
        utilisateur utilisateur = utilisateurOptional.get();
        role role = roleOptional.get();
        utilisateur.setRole(role); // Assigner le rôle

        // Sauvegarder les changements
        utilisateurService.saveUtilisateur(utilisateur);

        return ResponseEntity.ok("Rôle associé avec succès à l'utilisateur.");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'association du rôle.");
    }
}



    private  UtilisateurImageService utilisateurImageService;


    // Injection des services
    public UtilisateurController(UtilisateurService utilisateurService, UtilisateurImageService utilisateurImageService) {
        this.utilisateurService = utilisateurService;
        this.utilisateurImageService = utilisateurImageService;
    }

    // Ajouter une image à un utilisateur
    @PostMapping("/{utilisateurId}/image")
    public ResponseEntity<String> addImageToUtilisateur(@PathVariable Long utilisateurId, 
                                                         @RequestParam("image") MultipartFile imageFile) {
        try {
            // Récupérer l'utilisateur
            Optional<utilisateur> utilisateurOptional = Optional.of(utilisateurService.getUtilisateurById(utilisateurId));
            if (!utilisateurOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
            }

            // Traiter l'image
            utilisateurImage utilisateurImage = new utilisateurImage();
            utilisateurImage.setNomImage(imageFile.getOriginalFilename());
            utilisateurImage.setCheminImage("/path/to/storage" + imageFile.getOriginalFilename()); // Remplacez par votre chemin réel

            // Sauvegarder l'image
            utilisateurImageService.saveUtilisateurImage(utilisateurImage);

            // Associer l'image à l'utilisateur
            utilisateur utilisateur = utilisateurOptional.get();
            utilisateur.setUtilisateurImage(utilisateurImage); // Associer l'image
            utilisateurService.saveUtilisateur(utilisateur); // Sauvegarder l'utilisateur avec l'image

            return ResponseEntity.ok("Image ajoutée avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de l'image.");
        }
    }

    // Suppression d'un Utilisateur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateurById(@PathVariable Long id) {
        try {
            boolean deleted = utilisateurService.deleteUtilisateurById(id);
            if (deleted) {
                return ResponseEntity.noContent().build();  // 204 No Content si la suppression est réussie
            } else {
                return ResponseEntity.notFound().build();  // 404 Not Found si l'utilisateur n'existe pas
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 Internal Server Error en cas d'erreur
        }
    }



    // Suppression d'une image d'un utilisateur par ID d'utilisateur et ID d'image
    @DeleteMapping("/{utilisateurId}/image/{imageId}")
    public ResponseEntity<String> deleteImageFromUtilisateur(
        @PathVariable Long utilisateurId, 
        @PathVariable Long imageId
    ) {
        try {
            utilisateurImageService.deleteImageFromUtilisateur(utilisateurId, imageId);
            return ResponseEntity.ok("Image supprimée avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Image ou utilisateur non trouvé.");
        }
    }
}
