package edu.miu.cs.neptune.repository;

import edu.miu.cs.neptune.domain.Auction;
import edu.miu.cs.neptune.domain.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findAllByAuctionStatus(AuctionStatus status);
    List<Auction> findAllByAuctionStatusAndEndDateAfter(AuctionStatus status, LocalDateTime now);

    @Modifying
    @Query(value = "DELETE FROM Auction a WHERE a.auctionId = :auctionId")
    void deleteById(Long auctionId);
}
