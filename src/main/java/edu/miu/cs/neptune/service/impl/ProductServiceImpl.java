package edu.miu.cs.neptune.service.impl;

import edu.miu.cs.neptune.Util.Util;
import edu.miu.cs.neptune.domain.Bid;
import edu.miu.cs.neptune.domain.Category;
import edu.miu.cs.neptune.domain.Product;
import edu.miu.cs.neptune.domain.ProductState;
import edu.miu.cs.neptune.repository.AuctionRepository;
import edu.miu.cs.neptune.repository.BiddingRepository;
import edu.miu.cs.neptune.repository.CategoryRepository;
import edu.miu.cs.neptune.repository.ProductRepository;
import edu.miu.cs.neptune.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BiddingRepository biddingRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Override
    public List<Product> getAll() {
        return Util.iterableToCollection(productRepository.findAll());
    }

    @Override
    public Page<Product> findProductsByProductStateEquals(ProductState state, int pageNum, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5,
                sortDir.equals("asc")? Sort.by(sortField).ascending():Sort.by(sortField).descending());
        return productRepository.findProductsByProductStateEquals(state, pageable);
    }


    @Override
    public Product getProductById(Long productID) {
        return productRepository.getProductByProductId(productID);
    }

    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.getProductsByCategoryId(categoryId);
    }

    @Override
    public Page<Product> listAll(int pageNum, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5,
                sortDir.equals("asc")? Sort.by(sortField).ascending():Sort.by(sortField).descending());
        return productRepository.findAll(pageable);

    }

    @Override
    public Page<Product> getProductsByCategoryIdAndProductStateEquals(Long id, ProductState state, int pageNum, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5,
                sortDir.equals("asc")? Sort.by(sortField).ascending():Sort.by(sortField).descending());

        return productRepository.getProductsByCategoryIdAndProductStateEquals(id,state, pageable);
    }


    @Override
    public Page<Product> findProductsByProductNameContaining(String keyword, int pageNum, String sortField, String sortDir) {
        return productRepository.findProductsByProductNameContaining(keyword.toUpperCase(), Pageable.unpaged());
    }


    @Override
    public Page<Product> findProductsByProductNameContainsOrProductNameContainsAndProductStateEquals(String keyword, String keywordUp, ProductState state, int pageNum, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum-1, 5,
                sortDir.equals("asc")?Sort.by(sortField).ascending():Sort.by(sortField).descending());
        return productRepository.findProductsByProductNameContainsOrProductNameContainsAndProductStateEquals(keyword, keywordUp, state, pageable);
    }

    @Override
    public Page<Product> findProductsByProductStateEqualsAndProductNameContainsOrProductNameContainsAndProductStateEquals(ProductState state,String name, String nameUp, ProductState productState,  int pageNum, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum-1, 5,
                sortDir.equals("asc")?Sort.by(sortField).ascending():Sort.by(sortField).descending());

        return productRepository.findProductsByProductStateEqualsAndProductNameContainsOrProductNameContainsAndProductStateEquals(state, name, nameUp, productState, pageable);
    }


    @Override
    public List<Category> findByCategoryId(Long id) {
        return productRepository.findByCategoryId(id);
    }

    @Override
    public Page<Product> findProductsByProductStateEqualsAndProductNameContaining(ProductState state, String name, int pageNum, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum-1, 5,
                sortDir.equals("asc")?Sort.by(sortField).ascending():Sort.by(sortField).descending());

        return productRepository.findProductsByProductStateEqualsAndProductNameContaining(state, name, pageable);
    }


    @Override
    public Product save(Product product) {
        product.setUploadDate(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public void delete(Long productId) {
        List<Bid> bids= biddingRepository.findBidsByProductId(productId);

        if(bids.size()>0) {
            throw new RuntimeException("Product has already been started bidding.");
        }
       Product product = productRepository.getProductByProductId(productId);
        Long auctionId = product.getAuction().getAuctionId();

        productRepository.deleteById(productId);
        auctionRepository.deleteById(auctionId);
    }

    @Override
    public List<Product> findProductsBySeller(String username) {
        return productRepository.findProductsBySeller(username);
    }

}
