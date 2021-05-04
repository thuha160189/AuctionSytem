package edu.miu.cs.neptune.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Admin {
    private static final String USER_NAME = "admin";
    private static final String PASS_WORD = "123456";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "admin_category", joinColumns = @JoinColumn(name = "admin_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "admin_customer", joinColumns = @JoinColumn(name = "admin_id"), inverseJoinColumns = @JoinColumn(name = "cust_id"))
    private List<User> verifyCustomers;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "admin_seller", joinColumns = @JoinColumn(name = "admin_id"), inverseJoinColumns = @JoinColumn(name = "seller_id"))
    private List<User> verifySellers;

    public Admin() {
        this.userName = USER_NAME;
        this.password = PASS_WORD;
    }

    public Admin(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static String getPassWord() {
        return PASS_WORD;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<User> getVerifyCustomers() {
        return verifyCustomers;
    }

    public void setVerifyCustomers(List<User> verifyCustomers) {
        this.verifyCustomers = verifyCustomers;
    }

    public List<User> getVerifySellers() {
        return verifySellers;
    }

    public void setVerifySellers(List<User> verifySellers) {
        this.verifySellers = verifySellers;
    }

    public void addVerifyCustomer(User customer) {
        if (customer == null) {
            throw new RuntimeException("The customer is not found.");
        }
        this.verifyCustomers.add(customer);
    }

    public void addVerifySeller(User seller) {
        if (seller == null) {
            throw new RuntimeException("The seller is not found.");
        }
        this.verifySellers.add(seller);
    }

    public void addCategory(Category category) {
        if (category == null) {
            throw new RuntimeException("The category is not found.");
        }
        this.categories.add(category);
    }
}
