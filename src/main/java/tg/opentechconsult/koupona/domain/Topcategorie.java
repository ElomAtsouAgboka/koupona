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
 * A Topcategorie.
 */
@Entity
@Table(name = "topcategorie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "topcategorie")
public class Topcategorie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom_top_categorie", nullable = false, unique = true)
    private String nomTopCategorie;

    @OneToMany(mappedBy = "topcategorie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Categorie> categories = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTopCategorie() {
        return nomTopCategorie;
    }

    public Topcategorie nomTopCategorie(String nomTopCategorie) {
        this.nomTopCategorie = nomTopCategorie;
        return this;
    }

    public void setNomTopCategorie(String nomTopCategorie) {
        this.nomTopCategorie = nomTopCategorie;
    }

    public Set<Categorie> getCategories() {
        return categories;
    }

    public Topcategorie categories(Set<Categorie> categories) {
        this.categories = categories;
        return this;
    }

    public Topcategorie addCategorie(Categorie categorie) {
        this.categories.add(categorie);
        categorie.setTopcategorie(this);
        return this;
    }

    public Topcategorie removeCategorie(Categorie categorie) {
        this.categories.remove(categorie);
        categorie.setTopcategorie(null);
        return this;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
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
        Topcategorie topcategorie = (Topcategorie) o;
        if (topcategorie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), topcategorie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Topcategorie{" +
            "id=" + getId() +
            ", nomTopCategorie='" + getNomTopCategorie() + "'" +
            "}";
    }
}
