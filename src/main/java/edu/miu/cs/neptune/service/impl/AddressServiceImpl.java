package edu.miu.cs.neptune.service.impl;

import edu.miu.cs.neptune.domain.Address;
import edu.miu.cs.neptune.domain.User;
import edu.miu.cs.neptune.repository.AddressRepository;
import edu.miu.cs.neptune.service.AddressService;
import edu.miu.cs.neptune.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {


  private final AddressRepository addressRepository;

  public AddressServiceImpl(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @Override
  public Address saveAddress(Address address) {
    return addressRepository.save(address);
  }

  @Override
  public List<Address> getAll() {
    return addressRepository.findAll();
  }

  @Override
  public Optional<Address> getById(Long addressId) {
    return addressRepository.findById(addressId);
  }
}
