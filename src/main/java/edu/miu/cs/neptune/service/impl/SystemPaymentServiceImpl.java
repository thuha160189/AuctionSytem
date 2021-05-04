package edu.miu.cs.neptune.service.impl;

import edu.miu.cs.neptune.domain.Auction;
import edu.miu.cs.neptune.domain.PaymentStatus;
import edu.miu.cs.neptune.domain.SystemPayment;
import edu.miu.cs.neptune.domain.User;
import edu.miu.cs.neptune.repository.SystemPaymentRepository;
import edu.miu.cs.neptune.service.SystemPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemPaymentServiceImpl implements SystemPaymentService {

    @Autowired
    private SystemPaymentRepository systemPaymentRepository;

    @Override
    public List<SystemPayment> getAll() {
        return null;
    }

    @Override
    public SystemPayment getById(Long id) {
        return null;
    }

    @Override
    public List<SystemPayment> getPaymentsByAuction(Long auctionId) {
        List<SystemPayment> listSystemPayment = systemPaymentRepository.findByAuctionId(auctionId);
        return listSystemPayment;
    }

    @Override
    public SystemPayment save(SystemPayment systemPayment) {
        return systemPaymentRepository.save(systemPayment);
    }

    @Override
    public SystemPayment getDepositPaymentByUser(User currentUser, Auction auction) {
        return systemPaymentRepository
                .findSystemPaymentByAuctionIdAndAndUserIdAndAndPaymentStatus(auction.getAuctionId(), currentUser.getUserId(), PaymentStatus.DEPOSITED)
                .orElse(null);
    }

}
