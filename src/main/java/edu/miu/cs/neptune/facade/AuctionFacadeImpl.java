package edu.miu.cs.neptune.facade;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import edu.miu.cs.neptune.domain.*;
import edu.miu.cs.neptune.service.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AuctionFacadeImpl implements AuctionFacade {

    private final UserService userService;
    private final BiddingService biddingService;
    private final AuctionService auctionService;
    private final ShippingService shippingService;
    private final SystemPaymentService systemPaymentService;
    private final MailService mailService;
    private final PaypalService paypalService;
    private final ProductService productService;

    public AuctionFacadeImpl(UserService userService, BiddingService biddingService,
                             AuctionService auctionService, ShippingService shippingService,
                             SystemPaymentService systemPaymentService, MailService mailService,
                             PaypalService paypalService, ProductService productService) {
        this.userService = userService;
        this.biddingService = biddingService;
        this.auctionService = auctionService;
        this.shippingService = shippingService;
        this.systemPaymentService = systemPaymentService;
        this.mailService = mailService;
        this.paypalService = paypalService;
        this.productService = productService;
    }

    @Override
    public Bid createBid(Bid bid, User user, Auction currentAuction) {

        if (bid.getBiddingTime().isAfter(currentAuction.getEndDate()) || !AuctionStatus.ACTIVE.equals(currentAuction.getAuctionStatus())) {
            throw new RuntimeException("The bidding time is ended.");
        }

        Bid currentHighestBid = biddingService.getHighestBidByAuctionId(currentAuction.getAuctionId()).orElse(null);
        if (currentHighestBid == null && bid.getBiddingAmount() < currentAuction.getBeginPrice()) {
            throw new RuntimeException("The bidding amount must be greater than the initial price.");
        } else if (currentHighestBid!= null && currentHighestBid.getBiddingAmount() >= bid.getBiddingAmount()) {
            throw new RuntimeException("The bidding amount must be greater than the previous one.");
        }
        //Bid savedBid = biddingService.save(bid);
        bid.setBidder(user);
        bid.setAuction(currentAuction);
        user.addBid(bid);
        currentAuction.addBid(bid);

        return  biddingService.save(bid);
    }

    @Override
    public List<Bid> getUserBidsByAuction(Long auctionId, User user) {
        List<Bid> userBids = user.getBids();
        return userBids.stream()
                .filter(bid -> auctionId.equals(bid.getAuction().getAuctionId()))
                .collect(Collectors.toList());
    }

    @Override
    public Auction closeAuction(Auction currentAuction) {

        if (currentAuction.getEndDate().isBefore(LocalDateTime.now()) && AuctionStatus.ACTIVE.equals(currentAuction.getAuctionStatus())) {
            currentAuction.setAuctionStatus(AuctionStatus.ENDED);
            if (currentAuction.getBids().size() > 0) {
                Bid highest = getTheHighestBid(currentAuction);
                currentAuction.setWinnerId(highest.getBidder().getUserId());
                Product product = currentAuction.getProduct();
                product.setPaymentDueDate(LocalDateTime.now().plusMinutes(3));
                productService.save(product);
                //return the deposit to the losers
                returnDeposit(currentAuction);
            }
            auctionService.save(currentAuction);
            return currentAuction;
        }
        return null;
    }

    @Override
    public boolean returnDeposit(Auction closedAuction) {

        if (!closedAuction.getAuctionStatus().equals(AuctionStatus.ENDED)) {
            throw new RuntimeException("The auction isn't finished yet.");
        }
        List<SystemPayment> systemPayments = systemPaymentService.getPaymentsByAuction(closedAuction.getAuctionId());
        systemPayments.forEach(payment -> {
            if (!payment.getUserId().equals(closedAuction.getWinnerId())) {
                paypalService.cancelPayment(payment.getSaleId());
                payment.setPaymentStatus(PaymentStatus.REFUNDED);
                systemPaymentService.save(payment);
            }
        });
        return false;
    }

    @Override
    public User winner(Long auctionId) {
        Optional<Auction> optAuction = auctionService.getById(auctionId);
        if (!optAuction.isPresent()) {
            throw new RuntimeException("The auction is not found.");
        }
        Long winnerId = optAuction.get().getWinnerId();
        return userService.getById(winnerId).orElse(null);
    }

    @Override
    public Bid getTheHighestBid(Auction auction) {
        if (auction == null) {
            throw new RuntimeException("The auction is not found.");
        }

        Bid highest = null;
        if (auction.getBids().size() > 0) {
            highest = auction.getBids()
                    .stream()
                    .max((bid1, bid2) -> bid1.getBiddingAmount().compareTo(bid2.getBiddingAmount()))
                    .get();

        }
        return highest;
    }

    @Override
    public List<Auction> getALlActiveAuctions() {
        return auctionService.getAllActiveAuctions();
    }

    @Override
    public Auction getAuctionById(Long auctionId) {
       return auctionService.getById(auctionId).orElse(null);
    }

    @Override
    public List<Auction> getAllAuctionsByUserId(long userId) {
        return auctionService.getAllByUserId(userId);
    }

    @Override
    public List<Auction> getAllAuctionsBySellerId(long sellerId) {
        return auctionService.getAllBySellerId(sellerId);
    }

    @Override
    public AuctionOrder getAuctionOrderByAuctionId(long auctionId, String userName) {
        return paypalService.getAuctionOrder(auctionId, userName);
    }

    @Override
    public Payment createPayment(Double price, String currency, String method, String intent, String description, String s, String s1) throws PayPalRESTException {
        return paypalService.createPayment(price, currency, method, intent, description, s, s1);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        return paypalService.executePayment(paymentId, payerId);
    }

    @Override
    public Auction saveAuction(Auction auction) {
        return auctionService.save(auction);
    }

    @Override
    public User getUserByUserName(String name) {
        return userService.getByName(name).orElse(null);
    }

    @Override
    public void productSold(Long auctionId) {
        auctionService.productSold(auctionId);
    }

    @Override
    public List<Auction> closingEndedAuctions() {
        List<Auction> endedAuctions = new ArrayList<>();
        auctionService.getAll().forEach(auction -> {
            Auction ended = closeAuction(auction);
            if (ended != null) {
                endedAuctions.add(ended);
            }
        });
        return endedAuctions;
    }

    @Override
    public void finalizePayment(String authorizationId, Double unitAmount) {
        paypalService.finalizePayment(authorizationId, unitAmount);
    }

    @Override
    public void cancelPayment(String authorizationId) {
        paypalService.cancelPayment(authorizationId);
    }

    @Override
    public boolean refundProductPayment(Long auctionId, Long userId) {
        // delivery time is expired, need to refund the money back
        List<SystemPayment> listSystemPayment = systemPaymentService.getPaymentsByAuction(auctionId);
        for (SystemPayment systemPayment : listSystemPayment) {
            if (systemPayment.getUserId()==userId ) { //&& systemPayment.getPaymentType()==PaymentType.PRODUCT_PAYMENT && systemPayment.getPaymentStatus()==PaymentStatus.PAID
                String authorizationId = systemPayment.getSaleId();
                System.out.println("authorizationId:"+authorizationId+", is refunded");
                cancelPayment(authorizationId);
                systemPayment.setPaymentStatus(PaymentStatus.REFUNDED);
                systemPaymentService.save(systemPayment);
                //return true;
            }
        }
        return false;
    }

    @Override
    public boolean payTheProduct(Long auctionId, Long userId) {

        List<SystemPayment> listSystemPayment = systemPaymentService.getPaymentsByAuction(auctionId);
        if (listSystemPayment==null) {
            System.out.println("no payment history found for auctionId:"+auctionId);
            return false;
        }

        System.out.println("number of authorizations:"+listSystemPayment.size());
        for (SystemPayment systemPayment : listSystemPayment) {
            if (systemPayment.getUserId()==userId) { //systemPayment.getPaymentType()==PaymentType.PRODUCT_PAYMENT  && systemPayment.getPaymentStatus()==PaymentStatus.PAID
                String authorizationId = systemPayment.getSaleId();

                System.out.println("authorizationId:"+authorizationId);
                System.out.println("Amount is paid:"+systemPayment.getPaymentAmount());

                finalizePayment(authorizationId, systemPayment.getPaymentAmount());
                systemPayment.setPaymentStatus(PaymentStatus.SENT);
                systemPaymentService.save(systemPayment);
                //return true;
            }
        }
        return false;
    }

    // buy didn't pay for the product after winning
    @Override
    public boolean chargeTheDeposit(Long auctionId, Long userId) {
        List<SystemPayment> listSystemPayment = systemPaymentService.getPaymentsByAuction(auctionId);
        if (listSystemPayment==null) {
            System.out.println("no payment history found for auctionId:"+auctionId);
            return false;
        }

        for (SystemPayment systemPayment : listSystemPayment) {
            if (systemPayment.getUserId()==userId) { //systemPayment.getPaymentType()==PaymentType.PRODUCT_PAYMENT  //&& systemPayment.getPaymentStatus()==PaymentStatus.PAID
                String authorizationId = systemPayment.getSaleId();

                System.out.println("authorizationId:"+authorizationId);
                System.out.println("Amount is paid:"+systemPayment.getPaymentAmount());
                System.out.println("buyer didn't pay after winning");

                finalizePayment(authorizationId, systemPayment.getPaymentAmount());
                systemPayment.setPaymentStatus(PaymentStatus.SENT);
                systemPaymentService.save(systemPayment);
                return true;
            }
        }


        return false;
    }

    @Override
    public User getUserByUserId(long userId) {
        return userService.getById(userId).orElse(null);
    }

    @Override
    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    @Override
    public SystemPayment getDepositPaymentByUser(User currentUser, Auction auction) {
        return systemPaymentService.getDepositPaymentByUser(currentUser, auction);
    }

    @Override
    public AuctionOrder getDepositAuctionOrder(Long auctionId, String username) {
        Auction auction = auctionService.getById(auctionId).orElse(null);
        User user = userService.getByName(username).orElse(null);
        if (auction != null && user != null) {
            return paypalService.getDepositAuctionOrder(auction, user);
        }
        return null;
    }

    @Override
    public SystemPayment savePayment(SystemPayment systemPayment) {
        return systemPaymentService.save(systemPayment);
    }

}
