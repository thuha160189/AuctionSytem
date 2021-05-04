package edu.miu.cs.neptune.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuctionOrder {
    private String description;
    private Double price;
    private User user;
    private String currency="USD";
    private String method="paypal";
    private String intent="SALE";

    private Long auctionId;

    @Override
    public String toString() {
        return "AuctionOrder{" +
                "description='" + description + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", method='" + method + '\'' +
                ", intent='" + intent + '\'' +
                ", user='" + user.getUserId() + '\'' +
                ", auctionId='" + auctionId + '\'' +
                '}';
    }
}
