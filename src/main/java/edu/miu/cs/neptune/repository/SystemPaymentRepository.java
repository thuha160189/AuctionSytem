package edu.miu.cs.neptune.repository;

import edu.miu.cs.neptune.domain.PaymentStatus;
import edu.miu.cs.neptune.domain.Shipping;
import edu.miu.cs.neptune.domain.SystemPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface SystemPaymentRepository extends JpaRepository<SystemPayment, Long> {
    public Optional<SystemPayment> findSystemPaymentByAuctionIdAndAndUserIdAndAndPaymentStatus(Long auctionId, Long userId, PaymentStatus paymentStatus);

    @Query("SELECT p FROM SystemPayment p WHERE p.auctionId=:auctionId")
    public List<SystemPayment> findByAuctionId(@Param("auctionId") Long auctionId);
}
