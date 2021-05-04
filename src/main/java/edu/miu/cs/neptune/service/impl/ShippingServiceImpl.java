package edu.miu.cs.neptune.service.impl;

import edu.miu.cs.neptune.Util.Util;
import edu.miu.cs.neptune.domain.Auction;
import edu.miu.cs.neptune.domain.Shipping;
import edu.miu.cs.neptune.repository.ShippingRepository;
import edu.miu.cs.neptune.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    ShippingRepository shippingRepository;


    @Override
    public List<Shipping> getAll() {
        return Util.iterableToCollection(shippingRepository.findAll());
    }

    @Override
    public Optional<Shipping> getById(Long id) {
        return shippingRepository.findById(id);
    }

    @Override
    public Shipping save(Shipping shipping) {
        return shippingRepository.save(shipping);
    }

    @Override
    public Shipping getByProductId(Long productId) {
        return shippingRepository.findByProductId(productId) ;
    }



}
