package org.minecraftegory.controllers;

import org.minecraftegory.DTO.CategoryCommand;
import org.minecraftegory.DTO.CategoryDTO;
import org.minecraftegory.DTO.PaginatedDTO;
import org.minecraftegory.entities.Category;
import org.minecraftegory.enums.Sorting;
import org.minecraftegory.exceptions.CategoryNotFoundException;
import org.minecraftegory.exceptions.InvalidParentException;
import org.minecraftegory.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/page/{page}")
    public PaginatedDTO getPaginatedCategories(@PathVariable int page,
                                               @RequestParam int categoriesPerPage,
                                               @RequestParam int sortValue) {
        Sorting sort = Sorting.getByValue(sortValue);
        return categoryService.getPaginatedDTO(page,
                categoryService.getPaginatedCategories(page, categoriesPerPage, sort),
                categoryService.getAllCategories().size(), categoriesPerPage);
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategory(@PathVariable int id) throws CategoryNotFoundException {
        return categoryService.getDTO(categoryService.getCategory(id));
    }

    @GetMapping("/parent/available/{id}")
    public List<CategoryDTO> getAvailableParent(@PathVariable int id) throws CategoryNotFoundException {
        Category category = categoryService.getCategory(id);
        return categoryService.getDTOList(categoryService.getAvailableParents(category));
    }

    @GetMapping("/children/{id}")
    public List<CategoryDTO> getChildrenCategories(@PathVariable int id) throws CategoryNotFoundException {
        Category category = categoryService.getCategory(id);
        return categoryService.getDTOList(categoryService.getChildrenCategories(category));
    }

    @GetMapping("/search/{term}")
    public PaginatedDTO searchCategories(@PathVariable String term, @RequestParam int page,
                                         @RequestParam int categoriesPerPage,
                                         @RequestParam int sortValue) {
        Sorting sort = Sorting.getByValue(sortValue);
        return categoryService.getPaginatedDTO(page,
                categoryService.searchPaginatedCategories(term, page, categoriesPerPage, sort),
                categoryService.searchCategories(term).size(), categoriesPerPage);
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