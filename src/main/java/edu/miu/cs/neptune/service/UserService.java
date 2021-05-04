package edu.miu.cs.neptune.service;

import edu.miu.cs.neptune.domain.ProfileVerificationType;
import edu.miu.cs.neptune.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
  User saveUser(User user);

  User updateUser(User user);
  User updatePassword(User user);
  User forgotPasswordSendVC(User user);

  List<User> getAll();

  Optional<User> getById(Long userId);

  Optional<User> getByName(String username);

  void sendVerificationCode(String mailSubject, User user);
  void generateVerificationCode(User user);

  //Get list all pending profile to be verified.
  List<User> findAllPendingProfile();

//  void update(User user);
//
//  void inactive(String username);
}
