package edu.miu.cs.neptune.service.impl;

import edu.miu.cs.neptune.Util.Util;
import edu.miu.cs.neptune.domain.Category;
import edu.miu.cs.neptune.repository.CategoryRepository;
import edu.miu.cs.neptune.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return Util.iterableToCollection(categoryRepository.findAll());
    }

    @Override
    public Category save(Category category) {
        Optional<Category> foundOptional= categoryRepository.findByName(category.getCategoryName());

        if(foundOptional.isPresent()) {
            Category foundCategory = foundOptional.orElse(null);
            if(foundCategory.getCategoryId() != category.getCategoryId()) {
                throw new RuntimeException("Duplicate category name");
            }
        }
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long categoryId) {
        if(categoryRepository.countProductsByCategoryId(categoryId)>0) {
            throw new RuntimeException("There are products belong to the category");
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
