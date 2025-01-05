package org.minecraftegory.services;

import org.minecraftegory.entities.Category;
import org.minecraftegory.exceptions.CategoryNotFoundException;
import org.minecraftegory.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategory(int id) throws CategoryNotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("category not found - " + id));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getParentCategory(Category category) {
        return category.getParent();
    }

    public List<Category> getChildrenCategories(Category category) {
        return category.getChildren();
    }

    public Category createCategory(String name, Category parent) {
        Category category = new Category();
        category.setName(name);
        category.setCreationDate(new Date());
        category.setParent(parent);
        category.setChildren(new ArrayList<>());
        categoryRepository.save(category);
        return category;
    }

    public Category updateCategory(Category category, String name, Category parent) {
        category.setName(name);
        category.setParent(parent);
        parent.addChild(category);
        categoryRepository.save(category);
        categoryRepository.save(parent);
        return category;
    }

    public void deleteCategory(Category category) {
        for (Category child : category.getChildren()) {
            deleteCategory(child);
        }
        categoryRepository.delete(category);
    }

    public List<Category> searchCategories(String term) {
        return categoryRepository.getCategoriesByNameContaining(term);
    }

    public boolean isCategoryInvalid(Category category, Category parent) {
        return parent != null && category.getId() == parent.getId();
    }

    public boolean isCategoryRoot(Category category) {
        return category.getParent() == null;
    }
}
