package com.tpspring.tp3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpspring.tp3.Entity.utilisateur;
import com.tpspring.tp3.Entity.utilisateurImage;
import com.tpspring.tp3.repository.UtilisateurImageRepository;
import com.tpspring.tp3.repository.UtilisateurRepository;

@Service
public class UtilisateurImageService {

    private final UtilisateurImageRepository utilisateurImageRepository;
@Autowired
    private UtilisateurRepository utilisateurRepository;

    
    @Autowired
    public UtilisateurImageService(UtilisateurImageRepository utilisateurImageRepository) {
        this.utilisateurImageRepository = utilisateurImageRepository;
    }

    public void saveUtilisateurImage(utilisateurImage utilisateurImage) {
        utilisateurImageRepository.save(utilisateurImage);
 
 
    }

    // methode pour supprimer l'image d'un utilisateur
    public void deleteImageFromUtilisateur(Long utilisateurId, Long imageId) {
        // Vérifier si l'utilisateur existe
        utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier si l'image existe et est associée à l'utilisateur
        utilisateurImage utilisateurImage = utilisateurImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image non trouvée"));

        // Vérification que l'image appartient bien à cet utilisateur
        if (utilisateurImage.getUtilisateur().getId().equals(utilisateurId)) {
            // Supprimer l'image de l'utilisateur
            utilisateur.setUtilisateurImage(null); // Déassociée de l'utilisateur
            utilisateurRepository.save(utilisateur);  // Sauvegarder les changements
            utilisateurImageRepository.delete(utilisateurImage);  // Supprimer l'image
        } else {
            throw new RuntimeException("L'image ne correspond pas à l'utilisateur.");
        }
    }
}
