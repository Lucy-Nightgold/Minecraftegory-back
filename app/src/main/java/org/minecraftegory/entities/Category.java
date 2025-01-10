package org.minecraftegory.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    private String name;

    @Setter
    private Date creationDate;

    @ManyToOne
    @Setter
    private Category parent;

    @OneToMany
    @Setter
    private List<Category> children;

    public void addChild(Category child) {
        children.add(child);
    }

    public void removeChild(Category child) {
        children.remove(child);
    }

    public boolean isDescendantOf(Category category) {
        Category ancestor = getParent() != null ? getParent() : null;
        while (ancestor != null) {
            if (ancestor.getId() == category.getId()) {
                return true;
            }
            ancestor = ancestor.getParent();
        }
        return false;
    }
}
