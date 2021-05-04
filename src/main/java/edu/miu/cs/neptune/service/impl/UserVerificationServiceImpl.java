package edu.miu.cs.neptune.service.impl;

import edu.miu.cs.neptune.domain.UserVerification;
import edu.miu.cs.neptune.repository.UserRepository;
import edu.miu.cs.neptune.repository.UserVerificationRepository;
import edu.miu.cs.neptune.service.GenerateService;
import edu.miu.cs.neptune.service.MailService;
import edu.miu.cs.neptune.service.UserVerificationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserVerificationServiceImpl implements UserVerificationService {

  private UserVerificationRepository userVerificationRepository;

  public UserVerificationServiceImpl(UserVerificationRepository userVerificationRepository) {
    this.userVerificationRepository = userVerificationRepository;
  }

  @Override
  public UserVerification save(UserVerification userVerification) {
    return userVerificationRepository.save(userVerification);
  }

  @Override
  public Optional<UserVerification> getByUserId(Long userId) {
    return userVerificationRepository.findByUserId(userId);
  }
}
