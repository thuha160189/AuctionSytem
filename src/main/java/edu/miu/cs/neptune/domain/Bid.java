package edu.miu.cs.neptune.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Double biddingAmount;

    @Column(name = "bidding_time")
    private LocalDateTime biddingTime;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User bidder;

    public Bid() {}

    public Bid(Double biddingAmount, LocalDateTime biddingTime) {
        this.biddingAmount = biddingAmount;
        this.biddingTime = biddingTime;
    }

    public Auction getAuction() {
        return auction;
    }

    public User getBidder() {
        return bidder;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public Long getId() {
        return id;
    }

    public Double getBiddingAmount() {
        return biddingAmount;
    }

    public void setBiddingAmount(Double biddingAmount) {
        this.biddingAmount = biddingAmount;
    }

    public LocalDateTime getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(LocalDateTime biddingTime) {
        this.biddingTime = biddingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return id.equals(bid.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", biddingAmount=" + biddingAmount +
                ", biddingTime=" + biddingTime +
                '}';
    }
}
