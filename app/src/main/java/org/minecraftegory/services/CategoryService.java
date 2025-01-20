package org.minecraftegory.services;

import org.minecraftegory.DTO.CategoryDTO;
import org.minecraftegory.entities.Category;
import org.minecraftegory.exceptions.CategoryNotFoundException;
import org.minecraftegory.exceptions.InvalidParentException;
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

    public List<Category> getRootCategories() {
        return getAllCategories().stream().filter(this::isCategoryRoot).toList();
    }

    public List<Category> getAvailableParents(Category category) {
        return getAllCategories().stream().filter(parent -> !isCategoryInvalid(category, parent)).toList();
    }

    public Category createCategory(String name, Category parent) {
        Category category = new Category();
        category.setName(name);
        category.setCreationDate(new Date());
        category.setParent(parent);
        category.setChildren(new ArrayList<>());
        categoryRepository.save(category);
        if (parent != null) {
            parent.addChild(category);
            categoryRepository.save(parent);
        }
        return category;
    }

    public Category updateCategory(int categoryId, String name, Category parent) throws CategoryNotFoundException, InvalidParentException {
        Category category = getCategory(categoryId);
        Category oldParent = getParentCategory(category);
        if (isCategoryInvalid(category, parent)) {
            throw new InvalidParentException("error - invalid new parent for category " + categoryId);
        }
        if (!isCategoryRoot(category)) {
            oldParent.removeChild(category);
            categoryRepository.save(oldParent);
        }
        if (parent != null && !parent.getChildren().contains(category)) {
            parent.addChild(category);
            categoryRepository.save(parent);
        }
        category.setName(name);
        category.setParent(parent);
        categoryRepository.save(category);
        return category;
    }

    public void deleteCategory(Category category) {
        for (int i = 0; i < category.getChildren().size(); i++) {
            deleteCategory(category.getChildren().get(i));
        }
        Category parent = category.getParent();
        if (parent != null) {
            parent.removeChild(category);
            categoryRepository.save(parent);
        }
        categoryRepository.delete(category);
    }

    public List<Category> searchCategories(String term) {
        return categoryRepository.getCategoriesByNameContaining(term);
    }

    public boolean isCategoryInvalid(Category category, Category parent) {
        System.out.println("parent: " + (parent == null) + ": " + (parent != null ? parent.isDescendantOf(category): ""));
        return parent != null && (category.getId() == parent.getId() || parent.isDescendantOf(category));
    }

    public boolean isCategoryRoot(Category category) {
        return category.getParent() == null;
    }

    public CategoryDTO getDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setCreationDate(category.getCreationDate());
        dto.setRoot(isCategoryRoot(category));
        dto.setChildrenId(getChildrenCategories(category).stream().map(Category::getId).toList());
        dto.setParentId(isCategoryRoot(category) ? 0: getParentCategory(category).getId());
        return dto;
    }

    public List<CategoryDTO> getDTOList(List<Category> categories) {
        return categories.stream()
                .map(this::getDTO)
                .toList();
    }
}
