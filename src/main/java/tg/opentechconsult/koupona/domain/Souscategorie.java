package tg.opentechconsult.koupona.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Souscategorie.
 */
@Entity
@Table(name = "souscategorie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "souscategorie")
public class Souscategorie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom_sous_categorie", nullable = false)
    private String nomSousCategorie;

    @ManyToOne
    private Categorie categorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSousCategorie() {
        return nomSousCategorie;
    }

    public Souscategorie nomSousCategorie(String nomSousCategorie) {
        this.nomSousCategorie = nomSousCategorie;
        return this;
    }

    public void setNomSousCategorie(String nomSousCategorie) {
        this.nomSousCategorie = nomSousCategorie;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Souscategorie categorie(Categorie categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
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
        Souscategorie souscategorie = (Souscategorie) o;
        if (souscategorie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), souscategorie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Souscategorie{" +
            "id=" + getId() +
            ", nomSousCategorie='" + getNomSousCategorie() + "'" +
            "}";
    }
}
