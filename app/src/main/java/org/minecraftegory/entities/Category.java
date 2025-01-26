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
    private List<Category> children;

    private int childrenNumber;

    public void addChild(Category child) {
        children.add(child);
        this.childrenNumber = children.size();
    }

    public void removeChild(Category child) {
        if (children.contains(child)) {
            children.remove(child);
            this.childrenNumber = children.size();
        }
    }

    public void setChildren(List<Category> children) {
        this.children = children;
        this.childrenNumber = children.size();
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
