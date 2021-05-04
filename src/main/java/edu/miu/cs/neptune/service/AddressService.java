package edu.miu.cs.neptune.service;

import edu.miu.cs.neptune.domain.Address;
import edu.miu.cs.neptune.domain.User;

import java.util.List;
import java.util.Optional;

public interface AddressService {
  Address saveAddress(Address address);

  List<Address> getAll();

  Optional<Address> getById(Long addressId);

//  void update(User user);
//
//  void inactive(String username);
}
