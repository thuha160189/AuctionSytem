package edu.miu.cs.neptune.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class SystemPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Long auctionId;
    private Long userId;

    @NotNull
    private Double paymentAmount;

    private Double balance;

    @NotNull
    private PaymentStatus paymentStatus;

    @NotNull
    private PaymentType paymentType;

    //Used for paypal
    private String saleId;

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public SystemPayment() {}

    public SystemPayment(Long auctionId, Long userId, @NotNull Double paymentAmount, @NotNull PaymentStatus paymentStatus, @NotNull PaymentType paymentType, String saleId) {
        this.auctionId = auctionId;
        this.userId = userId;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
        this.saleId = saleId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Double getBalance() {
        return balance;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public Long getUserId() {
        return userId;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus status) {
        this.paymentStatus = status;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount, PaymentStatus paymentStatus) {
        this.balance -= amount;
        setPaymentStatus(paymentStatus);
    }
}
