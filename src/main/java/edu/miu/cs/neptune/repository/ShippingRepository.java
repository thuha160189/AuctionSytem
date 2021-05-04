package edu.miu.cs.neptune.repository;

import edu.miu.cs.neptune.domain.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {

    @Query("SELECT p FROM Shipping p WHERE p.productId=:productId")
    public Shipping findByProductId(@Param("productId") Long productId);
}
