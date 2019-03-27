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
 * A Menu.
 */
@Entity
@Table(name = "menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "menu_item", nullable = false, unique = true)
    private String menuItem;

    @Column(name = "menu_item_img")
    private String menuItemImg;

    @OneToMany(mappedBy = "menu")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Sousmenu> sousmenus = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public Menu menuItem(String menuItem) {
        this.menuItem = menuItem;
        return this;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public String getMenuItemImg() {
        return menuItemImg;
    }

    public Menu menuItemImg(String menuItemImg) {
        this.menuItemImg = menuItemImg;
        return this;
    }

    public void setMenuItemImg(String menuItemImg) {
        this.menuItemImg = menuItemImg;
    }

    public Set<Sousmenu> getSousmenus() {
        return sousmenus;
    }

    public Menu sousmenus(Set<Sousmenu> sousmenus) {
        this.sousmenus = sousmenus;
        return this;
    }

    public Menu addSousmenu(Sousmenu sousmenu) {
        this.sousmenus.add(sousmenu);
        sousmenu.setMenu(this);
        return this;
    }

    public Menu removeSousmenu(Sousmenu sousmenu) {
        this.sousmenus.remove(sousmenu);
        sousmenu.setMenu(null);
        return this;
    }

    public void setSousmenus(Set<Sousmenu> sousmenus) {
        this.sousmenus = sousmenus;
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
        Menu menu = (Menu) o;
        if (menu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", menuItem='" + getMenuItem() + "'" +
            ", menuItemImg='" + getMenuItemImg() + "'" +
            "}";
    }
}
