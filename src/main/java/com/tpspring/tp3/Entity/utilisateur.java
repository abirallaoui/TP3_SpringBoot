package com.tpspring.tp3.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;

    // Constructeurs
    public utilisateur() {
    }

    public utilisateur(Long id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    // Getters et Setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Relations avec les autres entités

    // Relation OneToOne avec utilisateurImage
    @OneToOne(fetch = FetchType.EAGER)
    private utilisateurImage utilisateurImage;

    // Relation ManyToOne avec role
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("utilisateurs")
    private role role;

    // Getters et Setters pour les relations
    public utilisateurImage getUtilisateurImage() {
        return utilisateurImage;
    }

    public void setUtilisateurImage(utilisateurImage utilisateurImage) {
        this.utilisateurImage = utilisateurImage;
    }

    public role getRole() {
        return role;
    }

    public void setRole(role role) {
        this.role = role;
    }
}
