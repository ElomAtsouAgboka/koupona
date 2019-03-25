package tg.opentechconsult.koupona.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ville.
 */
@Entity
@Table(name = "ville")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ville")
public class Ville implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom_ville", nullable = false)
    private String nomVille;

    @ManyToOne
    @JsonIgnoreProperties("villes")
    private Pays pays;

    @OneToMany(mappedBy = "ville")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Quartier> quartiers = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomVille() {
        return nomVille;
    }

    public Ville nomVille(String nomVille) {
        this.nomVille = nomVille;
        return this;
    }

    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
    }

    public Pays getPays() {
        return pays;
    }

    public Ville pays(Pays pays) {
        this.pays = pays;
        return this;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Set<Quartier> getQuartiers() {
        return quartiers;
    }

    public Ville quartiers(Set<Quartier> quartiers) {
        this.quartiers = quartiers;
        return this;
    }

    public Ville addQuartier(Quartier quartier) {
        this.quartiers.add(quartier);
        quartier.setVille(this);
        return this;
    }

    public Ville removeQuartier(Quartier quartier) {
        this.quartiers.remove(quartier);
        quartier.setVille(null);
        return this;
    }

    public void setQuartiers(Set<Quartier> quartiers) {
        this.quartiers = quartiers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ville ville = (Ville) o;
        if (ville.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ville.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ville{" +
            "id=" + getId() +
            ", nomVille='" + getNomVille() + "'" +
            "}";
    }
}
