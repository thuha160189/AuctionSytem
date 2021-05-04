package edu.miu.cs.neptune.service;

import edu.miu.cs.neptune.domain.Bid;
import edu.miu.cs.neptune.domain.User;

import java.util.List;
import java.util.Optional;

public interface BiddingService {
    Bid save(Bid bid);
    Bid getBidById(Long bidId);
    List<Bid> getAll();
    List<Bid> getUserBids(User user);
    Optional<Bid> getHighestBidByAuctionId(Long auctionId);

    //For seller
    Integer getNumberOfBidByProductId(Long productId);

}
