package edu.miu.cs.neptune.service;
import edu.miu.cs.neptune.domain.Shipping;


import java.util.List;
import java.util.Optional;

public interface ShippingService {
    List<Shipping> getAll();
    Optional<Shipping> getById(Long id);

    Shipping save(Shipping shipping);
    Shipping getByProductId(Long productId);
}
