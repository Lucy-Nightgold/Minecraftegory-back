package org.minecraftegory.repositories;

import org.minecraftegory.entities.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer>, PagingAndSortingRepository<Category, Integer> {
    List<Category> findCategoriesByParent(Category parent, Pageable pageable);

    List<Category> findCategoriesByNameContaining(String name);

    List<Category> findCategoriesByNameContaining(String name, Pageable pageable);
}
