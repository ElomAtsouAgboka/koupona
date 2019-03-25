package tg.opentechconsult.koupona.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Pays.
 */
@Entity
@Table(name = "pays")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pays")
public class Pays implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom_pays", nullable = false, unique = true)
    private String nomPays;

    @OneToMany(mappedBy = "pays")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ville> villes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomPays() {
        return nomPays;
    }

    public Pays nomPays(String nomPays) {
        this.nomPays = nomPays;
        return this;
    }

    public void setNomPays(String nomPays) {
        this.nomPays = nomPays;
    }

    public Set<Ville> getVilles() {
        return villes;
    }

    public Pays villes(Set<Ville> villes) {
        this.villes = villes;
        return this;
    }

    public Pays addVille(Ville ville) {
        this.villes.add(ville);
        ville.setPays(this);
        return this;
    }

    public Pays removeVille(Ville ville) {
        this.villes.remove(ville);
        ville.setPays(null);
        return this;
    }

    public void setVilles(Set<Ville> villes) {
        this.villes = villes;
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
        Pays pays = (Pays) o;
        if (pays.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pays.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pays{" +
            "id=" + getId() +
            ", nomPays='" + getNomPays() + "'" +
            "}";
    }
}
