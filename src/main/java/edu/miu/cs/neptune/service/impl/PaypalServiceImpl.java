package edu.miu.cs.neptune.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.HttpMethod;
import com.paypal.base.rest.PayPalRESTException;
import edu.miu.cs.neptune.domain.*;
import edu.miu.cs.neptune.service.AuctionService;
import edu.miu.cs.neptune.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaypalServiceImpl implements PaypalService {

    @Autowired
    AuctionService auctionService;

    @Autowired
    private APIContext apiContext;

    public AuctionOrder getAuctionOrder(Long auctionId, String userName) {
        Optional<Auction> auction = auctionService.getById(auctionId);

        if (auction.isPresent()) {
            Auction theAuction = auction.get();
            Product theProduct = theAuction.getProduct();

            Comparator<Bid> cmp = Comparator.comparing(Bid::getBiddingAmount);
            Bid highestBid = Collections.max(theAuction.getBids(), Comparator.comparing(b -> b.getBiddingAmount()));
            User theUser = highestBid.getBidder();

            if (!theUser.getUsername().equals(userName)) {
                return null;
            }

            if (theAuction.getProduct().getPaymentDueDate().compareTo(LocalDateTime.now())<0) {
                // payduedate is already passed
                return null;
            }

            // create paypal order object
            AuctionOrder auctionOrder = new AuctionOrder();
            auctionOrder.setDescription(theProduct.getProductName());
            System.out.println("highest bid amount:"+highestBid.getBiddingAmount());
            System.out.println("deposited amount:"+(theAuction.getDepositAmount()==null?theAuction.getBeginPrice()/10.0:theAuction.getDepositAmount()));
            double price = highestBid.getBiddingAmount() - (theAuction.getDepositAmount()==null?theAuction.getBeginPrice()/10.0:theAuction.getDepositAmount());
            auctionOrder.setPrice(price);
            auctionOrder.setUser(theUser);
            auctionOrder.setCurrency("USD");
            auctionOrder.setMethod("paypal");
            auctionOrder.setIntent("authorize");

            auctionOrder.setAuctionId(theAuction.getAuctionId());
            return auctionOrder;
        }
        return null;
    }

    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }


    @Override
    public void finalizePayment(String authorizationId, Double unitAmount) {

        Authorization authorization = new Authorization();
        authorization.setId(authorizationId);

        // Set capture amount
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(Double.toString(unitAmount));

        Capture capture = new Capture();
        capture.setAmount(amount);

        // Set as final capture amount
        capture.setIsFinalCapture(true);

        // Capture payment
        Capture responseCapture = null;

        apiContext.setRequestId((String)null);
        try {
            responseCapture = authorization.capture(apiContext, capture);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        System.out.println("Capture id = " + responseCapture.getId() + " and status = " + responseCapture.getState());
    }

    @Override
    public void cancelPayment(String authorizationId) {
        Authorization authorization = new Authorization();
        authorization.setId(authorizationId);
        apiContext.setRequestId((String)null);
        try {
            authorization.doVoid(apiContext);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AuctionOrder getDepositAuctionOrder(Auction theAuction, User user) {
        if (theAuction != null) {
            Product theProduct = theAuction.getProduct();
            // create paypal order object
            AuctionOrder auctionOrder = new AuctionOrder();
            auctionOrder.setDescription(theProduct.getProductName());
            auctionOrder.setPrice(theAuction.getDepositAmount());
            auctionOrder.setUser(user);
            auctionOrder.setCurrency("USD");
            auctionOrder.setMethod("paypal");
            auctionOrder.setIntent("DEPOSIT");

            auctionOrder.setAuctionId(theAuction.getAuctionId());
            return auctionOrder;
        }
        return null;
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    public Refund refundPayment(String saleId, double refundAmount) throws PayPalRESTException {
        Refund refund = new Refund();
        Amount amount = new Amount();
        amount.setTotal(Double.toString(refundAmount));
        amount.setCurrency("USD");
        refund.setAmount(amount);

        Sale sale = new Sale();
        sale.setId(saleId);

        return sale.refund(apiContext, refund);
    }

    public void sendFundToSeller() {
        //Todo return the fund back to people who lost the auction.
    }


}
