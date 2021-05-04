package edu.miu.cs.neptune.service;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Refund;
import com.paypal.base.rest.PayPalRESTException;
import edu.miu.cs.neptune.domain.Auction;
import edu.miu.cs.neptune.domain.Auction;
import edu.miu.cs.neptune.domain.AuctionOrder;
import edu.miu.cs.neptune.domain.User;
import edu.miu.cs.neptune.domain.User;

public interface PaypalService {
    Refund refundPayment(String saleId, double refundAmount) throws PayPalRESTException;
    void sendFundToSeller();
    AuctionOrder getAuctionOrder(Long auctionId, String userName);
    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
    Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException;
    void finalizePayment(String authorizationId, Double unitAmount);

    void cancelPayment(String authorizationId);

    AuctionOrder getDepositAuctionOrder(Auction auction, User user);

}
