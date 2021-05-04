package edu.miu.cs.neptune.service;


import edu.miu.cs.neptune.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAll();

    Category save(Category category);

    void delete(Long categoryId);

    Optional<Category> findById(Long categoryId);
}
