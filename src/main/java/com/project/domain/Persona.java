package com.project.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "persones")
public class Persona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clave primaria auto-generada
    @Column(name = "persona_id")
    private int personaId;

    @Column(nullable = false, unique = true, length = 9) // DNI único y obligatorio
    private String dni;

    @Column(nullable = false, length = 100) // Nombre obligatorio con longitud máxima
    private String nom;

    @Column(length = 15) // Teléfono opcional con longitud limitada
    private String telefon;

    @Column(nullable = false, unique = true, length = 100) // Email único y obligatorio
    private String email;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Prestec> prestecs;

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Prestec> getPrestecs() {
        return prestecs;
    }

    public void setPrestecs(List<Prestec> prestecs) {
        this.prestecs = prestecs;
    }

    /*
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Persona[id=%d, dni='%s', nom='%s'", 
            personaId, dni, nom));
        
        if (telefon != null) {
            sb.append(String.format(", tel='%s'", telefon));
        }
        if (email != null) {
            sb.append(String.format(", email='%s'", email));
        }
        
        int prestecsActius = getNumPrestecsActius();
        if (prestecsActius > 0) {
            sb.append(String.format(", prestecsActius=%d", prestecsActius));
            if (tePrestecsRetardats()) {
                sb.append(" (amb retards)");
            }
        }
        
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return personaId == persona.personaId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(personaId);
    }
    */
}