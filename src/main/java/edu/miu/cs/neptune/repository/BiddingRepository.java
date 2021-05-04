package edu.miu.cs.neptune.repository;

import edu.miu.cs.neptune.domain.AuctionStatus;
import edu.miu.cs.neptune.domain.Bid;
import edu.miu.cs.neptune.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BiddingRepository extends JpaRepository<Bid, Long> {
    @Query(value = "SELECT b FROM Bid b JOIN Product p ON b.auction.auctionId = p.auction.auctionId WHERE p.productId = :productId")
    List<Bid> findBidsByProductId(Long productId);

    @Query(value = "SELECT COUNT(b) FROM Bid b JOIN Product p ON b.auction.auctionId = p.auction.auctionId WHERE p.productId = :productId")
    Integer getNumberOfBidByProductId(Long productId);
}
