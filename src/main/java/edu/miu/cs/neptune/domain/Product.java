package edu.miu.cs.neptune.domain;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    //GANZO
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private Long categoryId;
    @NotBlank(message = "The product name is required.")
    private String productName;
    @NotNull(message = "The product price is required.")
    private Double productPrice;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime uploadDate;
    private String productDescription;
    private ProductStatus productStatus;
    private LocalDateTime paymentDueDate;

    @ManyToOne
    private User seller;

    @Transient
    private List<MultipartFile> images = new ArrayList<>();

    @OneToMany(cascade=CascadeType.ALL)
    private List<Image> dbImages =  new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Category> categories = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @Enumerated (EnumType.STRING)
    private ProductState productState = ProductState.SAVE_WITHOUT_RELEASE;

    public List<Image> getDbImages() {
        return dbImages;
    }

    public void setDbImages(List<Image> dbImages) {
        this.dbImages = dbImages;
    }

    public void addDbImage(Image dbImage) {
        this.dbImages.add(dbImage);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public void addImage(MultipartFile image) {
        this.images.add(image);
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public LocalDateTime getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(LocalDateTime paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public ProductState getProductState() {
        return productState;
    }

    public void setProductState(ProductState productState) {
        this.productState = productState;
    }
    @Override
    public String toString() {
        return "{" +
                '\"' + "productId" + '\"' + ':' + '\"'  +productId +
//                '\"' +",productState" + '\"' + ':' + '\"' +productState  +
                '\"' +";\"productName" + '\"'+ ':' + '\"' +productName +
                '\"' +";\"categoryId" + '\"'+ ':' + '\"' +categoryId +

                '\"' + ";\"productDescription" + '\"'+ ':' + '\"' +productDescription +
                '\"' + ";\"productPrice"+ '\"' + ':' + '\"' +productPrice +
                '\"' +";\"uploadDate" + '\"'+ ':' + '\"' +uploadDate + '\"' +
//                '\"' +",productStatus"+ '\"' + ':' + '\"' +productStatus +
//                '\"' +",paymentDueDate"+ '\"' + ':' + '\"' +paymentDueDate +
//                '\"' +",seller"+ '\"' + ':' + '\"' +seller +
//                '\"' +",images"+ '\"' + ':' + '\"' +images +
//                '\"' +",dbImages"+ '\"' + ':' + '\"' +dbImages +
//                '\"' +",categories"+ '\"' + ':' + '\"' +categories +
//                '\"' +",auction"+ '\"' + ':' + '\"' +auction +
                '}';
    }

}
