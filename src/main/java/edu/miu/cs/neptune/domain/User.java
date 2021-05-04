package edu.miu.cs.neptune.domain;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank
    private String username;// = "namnguyen";
    @Email
    @NotBlank
    private String email;// = "namco2011@gmail.com";
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;// = "Truong Thanh Nam";
    @NotBlank
    private String lastName;// = "Nguyen";
//    @NotBlank
    private String licenseNumber;
    @Enumerated (EnumType.STRING)
    private ProfileVerificationType profileVerificationType = ProfileVerificationType.NEED_TO_VERIFY;
    private String verificationCode = null;
    @Enumerated (EnumType.STRING)
    private UserVerificationType userVerificationType = UserVerificationType.NEED_TO_VERIFY;
    private LocalDateTime verificationCreationTime = LocalDateTime.now();
    private Integer failedVerificationCount = 0;
    private Boolean isResetPassword = false;
    private Boolean enable = true;
    @Enumerated (EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Invoice> invoices = new ArrayList<>();

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public User(Long userId, @NotBlank String username, @Email @NotBlank String email, @NotBlank String password, @NotBlank String firstName, @NotBlank String lastName, String licenseNumber, ProfileVerificationType profileVerificationType, String verificationCode, UserVerificationType userVerificationType, LocalDateTime verificationCreationTime, Integer failedVerificationCount, Boolean isResetPassword, Boolean enable, Role role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
        this.profileVerificationType = profileVerificationType;
        this.verificationCode = verificationCode;
        this.userVerificationType = userVerificationType;
        this.verificationCreationTime = verificationCreationTime;
        this.failedVerificationCount = failedVerificationCount;
        this.isResetPassword = isResetPassword;
        this.enable = enable;
        this.role = role;
    }

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "bidder", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Bid> bids = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> products = new ArrayList<>();


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public ProfileVerificationType getProfileVerificationType() {
        return profileVerificationType;
    }

    public void setProfileVerificationType(ProfileVerificationType profileVerificationType) {
        this.profileVerificationType = profileVerificationType;
    }

    public Boolean getResetPassword() {
        return isResetPassword;
    }

    public void setResetPassword(Boolean resetPassword) {
        isResetPassword = resetPassword;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public Boolean getEnable() {
        return enable;
    }

    public User setEnable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public User setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
        return this;
    }

    public UserVerificationType getUserVerificationType() {
        return userVerificationType;
    }

    public User setUserVerificationType(UserVerificationType userVerificationType) {
        this.userVerificationType = userVerificationType;
        return this;
    }

    public LocalDateTime getVerificationCreationTime() {
        return verificationCreationTime;
    }

    public User setVerificationCreationTime(LocalDateTime verificationCreationTime) {
        this.verificationCreationTime = verificationCreationTime;
        return this;
    }

    public Integer getFailedVerificationCount() {
        return failedVerificationCount;
    }

    public void increaseFailedVerificationCount() {
        this.failedVerificationCount +=1;
    }
    public void resetFailedVerificationCount() {
        this.failedVerificationCount =0;
    }
}
