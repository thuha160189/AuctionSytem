package edu.miu.cs.neptune.repository;

import edu.miu.cs.neptune.domain.Category;
import edu.miu.cs.neptune.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();

    @Query(value = "SELECT COUNT(p) FROM Product p join p.categories c WHERE c.categoryId = :categoryId")
    Long countProductsByCategoryId(@Param("categoryId")Long categoryId);

    @Query(value = "SELECT c FROM Category c WHERE c.categoryName = :categoryName")
    Optional<Category> findByName(String categoryName);
}
