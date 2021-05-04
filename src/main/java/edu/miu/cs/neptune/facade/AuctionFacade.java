package edu.miu.cs.neptune.facade;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import edu.miu.cs.neptune.domain.*;

import java.util.List;

public interface AuctionFacade {
    Bid createBid(Bid bid, User user, Auction auction);
    List<Bid> getUserBidsByAuction(Long auctionId, User user);
    Auction closeAuction(Auction auction);
    boolean returnDeposit(Auction closedAuction);
    User winner(Long auctionId);
    Bid getTheHighestBid(Auction auction);

    List<Auction> getALlActiveAuctions();

    Auction getAuctionById(Long auctionId);

    List<Auction> getAllAuctionsByUserId(long userId);

    List<Auction> getAllAuctionsBySellerId(long sellerId);

    AuctionOrder getAuctionOrderByAuctionId(long auctionId, String userName);

    Payment createPayment(Double price, String currency, String method, String intent, String description, String s, String s1) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

    Auction saveAuction(Auction auction);

    User getUserByUserName(String name);

    void productSold(Long auctionId);

    List<Auction> closingEndedAuctions();

    void finalizePayment(String authorizationId, Double unitAmount);

    void cancelPayment(String authorizationId);

    SystemPayment getDepositPaymentByUser(User currentUser, Auction auction);

    AuctionOrder getDepositAuctionOrder(Long auctionId, String username);

    SystemPayment savePayment(SystemPayment systemPayment);

    boolean refundProductPayment(Long auctionId, Long userId);

    User getUserByUserId(long parseLong);

    User updateUser(User user);

    boolean payTheProduct(Long auctionId, Long userId);

    boolean chargeTheDeposit(Long auctionId, Long userId);

//    boolean updateUserAddress(User)
}
