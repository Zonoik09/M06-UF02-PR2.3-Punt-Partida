package com.project.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "biblioteques")
public class Biblioteca implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "biblioteca_id")
    private int bibliotecaId;

    @Column(nullable = false)
    private String telefon;

    @Column(length = 100, nullable = false)
    private String nom;

    @Column(length = 100, nullable = false)
    private String ciutat;

    @Column(length = 150)
    private String adreca;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "biblioteca", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Exemplar> exemplars;

    public int getBibliotecaId() {
        return bibliotecaId;
    }

    public void setBibliotecaId(int bibliotecaId) {
        this.bibliotecaId = bibliotecaId;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCiutat() {
        return ciutat;
    }

    public void setCiutat(String ciutat) {
        this.ciutat = ciutat;
    }

    public String getAdreca() {
        return adreca;
    }

    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Exemplar> getExemplars() {
        return exemplars;
    }

    public void setExemplars(List<Exemplar> exemplars) {
        this.exemplars = exemplars;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Biblioteca[id=%d, nom='%s', ciutat='%s'", 
            bibliotecaId, nom, ciutat));
        
        if (adreca != null) {
            sb.append(String.format(", adreca='%s'", adreca));
        }
        if (telefon != null) {
            sb.append(String.format(", tel='%s'", telefon));
        }
        if (email != null) {
            sb.append(String.format(", email='%s'", email));
        }
        
        if (!exemplars.isEmpty()) {
            sb.append(", exemplars={");
            boolean first = true;
            for (Exemplar e : exemplars) {
                if (!first) sb.append(", ");
                sb.append(e.getCodiBarres());
                first = false;
            }
            sb.append("}");
        }
        
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biblioteca that = (Biblioteca) o;
        return bibliotecaId == that.bibliotecaId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(bibliotecaId);
    }

}