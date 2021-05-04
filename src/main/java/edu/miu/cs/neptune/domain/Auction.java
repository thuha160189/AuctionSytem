package edu.miu.cs.neptune.domain;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Auction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auctionId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime beginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    private AuctionStatus auctionStatus;
    private Double beginPrice;
    private Long winnerId;
    private Double depositAmount;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Bid> bids = new ArrayList<>();
    private LocalDateTime shippingDate;
    private ShippingStatus shippingStatus;
    @OneToOne(mappedBy = "auction", cascade = CascadeType.ALL)
    private Product product;

    public Auction() {}
    public Auction(LocalDateTime beginDate, LocalDateTime endDate, Double beginPrice, Double depositAmount) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.beginPrice = beginPrice;
        this.depositAmount = depositAmount;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public AuctionStatus getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(AuctionStatus auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public Double getBeginPrice() {
        return beginPrice;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerBidId) {
        this.winnerId = winnerBidId;
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

    public LocalDateTime getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDateTime shippingDate) {
        this.shippingDate = shippingDate;
    }

    public ShippingStatus getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(ShippingStatus shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setBeginPrice(Double beginPrice) {
        this.beginPrice = beginPrice;
    }

    public boolean isEnded() {
        return getEndDate().isBefore(LocalDateTime.now()) || AuctionStatus.ENDED.equals(getAuctionStatus());
    }

    @Override
    public String toString() {
        return "Auction{" +
                "auctionId=" + auctionId +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", auctionStatus=" + auctionStatus +
                ", beginPrice=" + beginPrice +
                ", winnerId=" + winnerId +
                ", bids=" + bids +
                ", shippingDate=" + shippingDate +
                ", shippingStatus=" + shippingStatus +
                ", product=" + product +
                '}';
    }
}
