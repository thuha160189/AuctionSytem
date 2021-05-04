package edu.miu.cs.neptune.service;

import edu.miu.cs.neptune.domain.Category;
import edu.miu.cs.neptune.domain.Product;
import edu.miu.cs.neptune.domain.ProductState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> getAll();
    Page<Product> findProductsByProductStateEquals(ProductState state, int pageNum, String sortField, String sortDir);

    Product getProductById(Long productID);
    List<Product> getProductsByCategoryId(Long categoryId);

    Page<Product> listAll(int pageNum, String sortField, String sortDir);
    Page<Product> getProductsByCategoryIdAndProductStateEquals(Long id,ProductState state, int pageNum, String sortField, String sortDir);

    Page<Product> findProductsByProductNameContaining(String keyword, int pageNum, String sortField, String sortDir);
    Page<Product> findProductsByProductNameContainsOrProductNameContainsAndProductStateEquals(String keyword, String keywordUp, ProductState state, int pageNum, String sortField, String sortDir);
    Page<Product> findProductsByProductStateEqualsAndProductNameContainsOrProductNameContainsAndProductStateEquals(ProductState state,String name, String nameUp, ProductState productState,  int pageNum, String sortField, String sortDir);
  //  Page<Product> findProductsByProductNameContainUppercase(String keyword, int pageNum, String sortField, String sortDir, int pageNum, String sortField, String sortDir);
    List<Category> findByCategoryId(Long id);
    Page<Product> findProductsByProductStateEqualsAndProductNameContaining(ProductState state, String name, int pageNum, String sortField, String sortDir);

    //For Sell module
    Product save(Product product);
    void delete(Long productId);
    List<Product> findProductsBySeller(String username);
}
