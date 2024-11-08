package com.tpspring.tp3.service;

import com.tpspring.tp3.Entity.role;
import com.tpspring.tp3.Entity.utilisateur;
import com.tpspring.tp3.Entity.utilisateurImage;
import com.tpspring.tp3.repository.RoleRepository;
import com.tpspring.tp3.repository.UtilisateurImageRepository;
import com.tpspring.tp3.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UtilisateurImageRepository utilisateurImageRepository;

    // methode pour créer un nouvel Utilisateur avec son role associé
    public utilisateur creerUtilisateur(String email, String nom, String nomRole) {
        Optional<role> roleOptional = roleRepository.findByNom(nomRole);
        if (roleOptional.isEmpty()) {
            throw new RuntimeException("Le role " + nomRole + " n'existe pas.");
        }
        
        utilisateur utilisateur = new utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setNom(nom); // Assurez-vous d'ajouter cette ligne pour définir le nom
        utilisateur.setRole(roleOptional.get());
    
        return utilisateurRepository.save(utilisateur);
    }
    

    // methode pour ajouter une image à un Utilisateur existant
    public utilisateur ajouterImageAUtilisateur(Long utilisateurId, String nomImage, String cheminImage) {
        Optional<utilisateur> utilisateurOptional = utilisateurRepository.findById(utilisateurId);
        if (utilisateurOptional.isPresent()) {
            utilisateur utilisateur = utilisateurOptional.get();
            utilisateurImage utilisateurImage = new utilisateurImage();
            utilisateurImage.setNomImage(nomImage);
            utilisateurImage.setCheminImage(cheminImage);
            utilisateurImage.setUtilisateur(utilisateur);
            utilisateurImageRepository.save(utilisateurImage);
            return utilisateur;
        } else {
            throw new RuntimeException("Utilisateur avec l'ID " + utilisateurId + " non trouvé.");
        }
    }

    // methode pour récupérer les Utilisateurs ayant un role spécifique
    public List<utilisateur> recupererUtilisateursParRole(String nomRole) {
        Optional<role> role = roleRepository.findByNom(nomRole);
        if (!role.isPresent()) {  
            throw new RuntimeException("Le role " + nomRole + " n'existe pas.");
        }
        return utilisateurRepository.findByRole(role.get());  // On passe le role trouvé ici
    }



        // methode pour récupérer tous les Utilisateurs
    public List<utilisateur> recupererTousLesUtilisateurs() {
        return utilisateurRepository.findAll(); // Utilisation de JpaRepository pour récupérer tous les utilisateurs
    }


    public utilisateur getUtilisateurById(Long id) {
        Optional<utilisateur> utilisateurOptional = utilisateurRepository.findById(id);
        if (utilisateurOptional.isPresent()) {
            return utilisateurOptional.get();
        } else {
            System.out.println("Utilisateur avec ID " + id + " non trouvé.");
            return null;  
        }
    }

    
    
    
    public void saveUtilisateur(utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
    }
    


    public boolean deleteUtilisateurById(Long id) {
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return true;
        }
        return false;  
    }


    
    
}
