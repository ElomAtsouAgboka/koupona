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
 * A Sousmenu.
 */
@Entity
@Table(name = "sousmenu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sousmenu")
public class Sousmenu implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "sous_menu_item", nullable = false)
    private String sousMenuItem;

    @Column(name = "sous_menu_item_img")
    private String sousMenuItemImg;

    @ManyToOne
    private Menu menu;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSousMenuItem() {
        return sousMenuItem;
    }

    public Sousmenu sousMenuItem(String sousMenuItem) {
        this.sousMenuItem = sousMenuItem;
        return this;
    }

    public void setSousMenuItem(String sousMenuItem) {
        this.sousMenuItem = sousMenuItem;
    }

    public String getSousMenuItemImg() {
        return sousMenuItemImg;
    }

    public Sousmenu sousMenuItemImg(String sousMenuItemImg) {
        this.sousMenuItemImg = sousMenuItemImg;
        return this;
    }

    public void setSousMenuItemImg(String sousMenuItemImg) {
        this.sousMenuItemImg = sousMenuItemImg;
    }

    public Menu getMenu() {
        return menu;
    }

    public Sousmenu menu(Menu menu) {
        this.menu = menu;
        return this;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
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
        Sousmenu sousmenu = (Sousmenu) o;
        if (sousmenu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sousmenu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sousmenu{" +
            "id=" + getId() +
            ", sousMenuItem='" + getSousMenuItem() + "'" +
            ", sousMenuItemImg='" + getSousMenuItemImg() + "'" +
            "}";
    }
}
