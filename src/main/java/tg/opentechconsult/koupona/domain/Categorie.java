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
 * A Categorie.
 */
@Entity
@Table(name = "categorie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "categorie")
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom_categorie", nullable = false, unique = true)
    private String nomCategorie;

    @ManyToOne
    @JsonIgnoreProperties("categories")
    private Topcategorie topcategorie;

    @OneToMany(mappedBy = "categorie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Souscategorie> souscategories = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public Categorie nomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
        return this;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public Topcategorie getTopcategorie() {
        return topcategorie;
    }

    public Categorie topcategorie(Topcategorie topcategorie) {
        this.topcategorie = topcategorie;
        return this;
    }

    public void setTopcategorie(Topcategorie topcategorie) {
        this.topcategorie = topcategorie;
    }

    public Set<Souscategorie> getSouscategories() {
        return souscategories;
    }

    public Categorie souscategories(Set<Souscategorie> souscategories) {
        this.souscategories = souscategories;
        return this;
    }

    public Categorie addSouscategorie(Souscategorie souscategorie) {
        this.souscategories.add(souscategorie);
        souscategorie.setCategorie(this);
        return this;
    }

    public Categorie removeSouscategorie(Souscategorie souscategorie) {
        this.souscategories.remove(souscategorie);
        souscategorie.setCategorie(null);
        return this;
    }

    public void setSouscategories(Set<Souscategorie> souscategories) {
        this.souscategories = souscategories;
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
        Categorie categorie = (Categorie) o;
        if (categorie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categorie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Categorie{" +
            "id=" + getId() +
            ", nomCategorie='" + getNomCategorie() + "'" +
            "}";
    }
}
