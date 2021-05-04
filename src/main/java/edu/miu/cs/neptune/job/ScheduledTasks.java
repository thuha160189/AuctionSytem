package edu.miu.cs.neptune.job;

import edu.miu.cs.neptune.domain.Auction;
import edu.miu.cs.neptune.domain.AuctionStatus;
import edu.miu.cs.neptune.domain.ShippingStatus;
import edu.miu.cs.neptune.domain.SystemPayment;
import edu.miu.cs.neptune.facade.AuctionFacade;
import edu.miu.cs.neptune.service.AuctionService;
import edu.miu.cs.neptune.service.SystemPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ScheduledTasks {

    @Autowired
    AuctionService auctionService;

    @Autowired
    SystemPaymentService systemPaymentService;


    @Autowired
    private AuctionFacade auctionFacade;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "MM/dd/yyyy HH:mm:ss");

//    @Scheduled(fixedRate = 10000)
//    public void performTask() {
//
//        System.out.println("Regular task performed at "
//                + dateFormat.format(new Date()));
//
//    }

    // check if user hasn't paid the product within allowed time,
    // if yes, remove the pay button, don't refund the deposit
    @Scheduled(initialDelay = 6000, fixedRate = 60000)
    public void performDelayedTask() {
        List<Auction> list = auctionService.getAllEndedAuction();
        for (Auction auction : list) {
            if (auction.getAuctionStatus() == AuctionStatus.ENDED) {
//                System.out.println("Auction is ended");
//                System.out.println("auctiondId:"+auction.getAuctionId());
//                System.out.println("payment Due date:"+auction.getProduct().getPaymentDueDate());
                LocalDateTime paymentDueDate = auction.getProduct().getPaymentDueDate();
                if (paymentDueDate!=null && paymentDueDate.compareTo(LocalDateTime.now())<0) {
                    // CASE 1
                    // Person didn't pay within paymentDueDate, charge the deposit
                    System.out.println("Auction is over but haven't paid within given time");
                    System.out.println("auctionProduct:"+auction.getProduct().getProductName());
                    // charge the deposit
                    auctionFacade.chargeTheDeposit(auction.getAuctionId(), auction.getWinnerId());
                    auction.setAuctionStatus(AuctionStatus.NOT_PAID);
                    auctionService.save(auction);
                }
            }

            if (auction.getShippingStatus()== ShippingStatus.IN_TRANSIT) {
                System.out.println(auction.getShippingDate());
                if (auction.getShippingDate().plusMinutes(1).compareTo(LocalDateTime.now())<0){
                    // delivery time is expired
                    // CASE 2
                    // Person paid, but he/she didn't receive it within given time.

                    auction.setShippingStatus(ShippingStatus.DELIVERY_EXPIRED);
                    auctionService.save(auction);
                    auctionFacade.refundProductPayment(auction.getAuctionId(),auction.getWinnerId());
                    System.out.println("delivery not made within time");
                    System.out.println("refunding the money back to userId:"+auction.getWinnerId());
                }
            }

        }

        System.out.println("Regular interval task performed at "
                + dateFormat.format(new Date()));

    }

}
