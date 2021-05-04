package edu.miu.cs.neptune.job;

import edu.miu.cs.neptune.domain.Auction;
import edu.miu.cs.neptune.facade.AuctionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class AuctionJobs {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private AuctionFacade auctionFacade;

    @Scheduled(fixedRate = 60000)
    public void closingEndedAuctions() {
        System.out.println("[Closing ended auctions]---The task started at "  + LocalDateTime.now().format(formatter));
        List<Auction> endedAuctions = auctionFacade.closingEndedAuctions();
        if (endedAuctions != null && !endedAuctions.isEmpty()) {
            endedAuctions.forEach(auction -> {
                System.out.println(auction);
            });
        }
        System.out.println("[Closing ended auctions]---The task ended at "  + LocalDateTime.now().format(formatter));
    }
}
