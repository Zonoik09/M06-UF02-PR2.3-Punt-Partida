package com.project.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
@Table(name = "prestecs")
public class Prestec implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prestec_id")
    private int prestecId;

    @ManyToOne
    @JoinColumn(name = "exemplar_id", nullable = false)
    private Exemplar exemplar;

    @ManyToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @Column(name = "data_prestec", nullable = false)
    private LocalDate dataPrestec;

    @Column(name = "data_retorn_prevista", nullable = false)
    private LocalDate dataRetornPrevista;

    @Column(name = "data_retorn_real")
    private LocalDate dataRetornReal;

    @Column(nullable = false)
    private boolean actiu;

    public int getPrestecId() {
        return prestecId;
    }

    public void setPrestecId(int prestecId) {
        this.prestecId = prestecId;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public LocalDate getDataPrestec() {
        return dataPrestec;
    }

    public void setDataPrestec(LocalDate dataPrestec) {
        this.dataPrestec = dataPrestec;
    }

    public LocalDate getDataRetornPrevista() {
        return dataRetornPrevista;
    }

    public void setDataRetornPrevista(LocalDate dataRetornPrevista) {
        this.dataRetornPrevista = dataRetornPrevista;
    }

    public LocalDate getDataRetornReal() {
        return dataRetornReal;
    }

    public void setDataRetornReal(LocalDate dataRetornReal) {
        this.dataRetornReal = dataRetornReal;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Prestec[id=%d", prestecId));
        
        if (exemplar != null) {
            sb.append(String.format(", exemplar='%s'", exemplar.getCodiBarres()));
        }
        
        if (persona != null) {
            sb.append(String.format(", persona='%s'", persona.getNom()));
        }
        
        sb.append(String.format(", dataPrestec='%s'", dataPrestec));
        sb.append(String.format(", dataRetornPrevista='%s'", dataRetornPrevista));
        
        if (dataRetornReal != null) {
            sb.append(String.format(", dataRetornReal='%s'", dataRetornReal));
        }
        
        sb.append(String.format(", actiu=%s", actiu));
        
        if (estaRetardat()) {
            sb.append(String.format(", diesRetard=%d", getDiesRetard()));
        }
        
        sb.append("]");
        return sb.toString();
    }

    private int getDiesRetard() {
        long diesRetard = DAYS.between(getDataRetornPrevista(), getDataRetornReal());
        return (int) Math.max(diesRetard, 0);
    }

    private boolean estaRetardat() {
        if (getDataRetornReal() != null && getDataRetornReal().isAfter(getDataRetornPrevista())) {
            return true;
        }
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prestec prestec = (Prestec) o;
        return prestecId == prestec.prestecId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(prestecId);
    }
}