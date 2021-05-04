package edu.miu.cs.neptune.service;

import edu.miu.cs.neptune.domain.Auction;
import edu.miu.cs.neptune.domain.SystemPayment;
import edu.miu.cs.neptune.domain.User;

import java.util.List;

public interface SystemPaymentService {
    List<SystemPayment> getAll();
    SystemPayment getById(Long id);
    List<SystemPayment> getPaymentsByAuction(Long auctionId);
    SystemPayment save(SystemPayment systemPayment);
    SystemPayment getDepositPaymentByUser(User currentUser, Auction auction);
}
