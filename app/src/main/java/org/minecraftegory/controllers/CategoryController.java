package org.minecraftegory.controllers;

import org.minecraftegory.DTO.CategoryCommand;
import org.minecraftegory.DTO.CategoryDTO;
import org.minecraftegory.entities.Category;
import org.minecraftegory.exceptions.CategoryNotFoundException;
import org.minecraftegory.exceptions.InvalidParentException;
import org.minecraftegory.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getDTOList(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategory(@PathVariable int id) throws CategoryNotFoundException {
        return categoryService.getDTO(categoryService.getCategory(id));
    }

    @GetMapping("/parent/{id}")
    public CategoryDTO getParentCategory(@PathVariable int id) throws CategoryNotFoundException {
        Category category = categoryService.getCategory(id);
        Category parent = categoryService.getParentCategory(category);
        return parent == null ? null: categoryService.getDTO(parent);
    }

    @GetMapping("/children/{id}")
    public List<CategoryDTO> getChildrenCategory(@PathVariable int id) throws CategoryNotFoundException {
        Category category = categoryService.getCategory(id);
        return categoryService.getDTOList(categoryService.getChildrenCategories(category));
    }

    @GetMapping("/root")
    public List<CategoryDTO> getRootCategories() {
        return categoryService.getDTOList(categoryService.getRootCategories());
    }

    @GetMapping("/search/{term}")
    public List<CategoryDTO> searchCategory(@PathVariable String term) {
        return categoryService.getDTOList(categoryService.searchCategories(term));
    }

    @PostMapping("")
    public CategoryDTO createCategory(@RequestBody CategoryCommand categoryCommand) throws CategoryNotFoundException {
        String name = categoryCommand.getName();
        Category parent = categoryCommand.getParentId() == 0 ? null : categoryService.getCategory(categoryCommand.getParentId());
        return categoryService.getDTO(categoryService.createCategory(name, parent));
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable int id, @RequestBody CategoryCommand categoryCommand) throws CategoryNotFoundException, InvalidParentException {
        String name = categoryCommand.getName();
        Category parent = categoryCommand.getParentId() == 0 ? null : categoryService.getCategory(categoryCommand.getParentId());
        return categoryService.getDTO(categoryService.updateCategory(id, name, parent));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id) throws CategoryNotFoundException {
        categoryService.deleteCategory(categoryService.getCategory(id));
    }
}