package com.project.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "llibres")
public class Llibre implements Serializable {
    //llibreId, isbn, titol, editorial, anyPublicacio, autors, exemplars
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "llibre_id")
    private int llibreId;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(length = 200, nullable = false)
    private String titol;

    @Column(length = 100)
    private String editorial;

    @Column(length = 4)
    private int anyPublicacio;

    @ManyToMany
    @JoinTable(
            name = "autors_llibres",
            joinColumns = @JoinColumn(name = "llibre_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autors;

    @OneToMany(mappedBy = "llibre", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Exemplar> exemplars;

    public int getLlibreId() {
        return llibreId;
    }

    public void setLlibreId(int llibreId) {
        this.llibreId = llibreId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getAnyPublicacio() {
        return anyPublicacio;
    }

    public void setAnyPublicacio(int anyPublicacio) {
        this.anyPublicacio = anyPublicacio;
    }

    public List<Autor> getAutors() {
        return autors;
    }

    public void setAutors(List<Autor> autors) {
        this.autors = autors;
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
        sb.append(String.format("Llibre[id=%d, isbn='%s', titol='%s'",
            llibreId, isbn, titol));

        if (editorial != null) {
            sb.append(String.format(", editorial='%s'", editorial));
        }
        if (anyPublicacio != 0) {
            sb.append(String.format(", any=%d", anyPublicacio));
        }

        if (!autors.isEmpty()) {
            sb.append(", autors={");
            boolean first = true;
            for (Autor a : autors) {
                if (!first) sb.append(", ");
                sb.append(a.getNom());
                first = false;
            }
            sb.append("}");
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
        Llibre llibre = (Llibre) o;
        return llibreId == llibre.llibreId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(llibreId);
    }
}