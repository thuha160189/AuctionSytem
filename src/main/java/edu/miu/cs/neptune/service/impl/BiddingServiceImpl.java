package edu.miu.cs.neptune.service.impl;

import edu.miu.cs.neptune.Util.Util;
import edu.miu.cs.neptune.domain.AuctionStatus;
import edu.miu.cs.neptune.domain.Bid;
import edu.miu.cs.neptune.domain.User;
import edu.miu.cs.neptune.repository.BiddingRepository;
import edu.miu.cs.neptune.service.BiddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BiddingServiceImpl implements BiddingService {
    @Autowired
    private BiddingRepository biddingRepository;

    @Override
    public Bid save(Bid bid) {
        return biddingRepository.save(bid);
    }

    @Override
    public Bid getBidById(Long bidId) {
        return biddingRepository.findById(bidId).orElse(null);
    }

    @Override
    public List<Bid> getAll() {
        return Util.iterableToCollection(biddingRepository.findAll());
    }

    @Override
    public List<Bid> getUserBids(User user) {
        return null;
    }

    @Override
    public Optional<Bid> getHighestBidByAuctionId(Long auctionId) {
        return Optional.empty();
    }

    @Override
    public Integer getNumberOfBidByProductId(Long productId) {
        return biddingRepository.getNumberOfBidByProductId(productId);
    }

}
