package edu.miu.cs.neptune.service;

import edu.miu.cs.neptune.domain.Auction;
import edu.miu.cs.neptune.domain.Category;

import java.util.List;
import java.util.Optional;

public interface AuctionService {
    List<Auction> getAll();
    Optional<Auction> getById(Long id);
    Auction save(Auction auction);
    //    @Override
    List<Auction> getAllByUserId(Long userId);
    List<Auction> getAllBySellerId(Long sellerId);
    List<Auction> getAllActiveAuctions();

    List<Auction> getAllEndedAuction();

    void productSold(Long id);
}
