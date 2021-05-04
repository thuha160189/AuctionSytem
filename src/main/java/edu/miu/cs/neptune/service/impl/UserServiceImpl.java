package edu.miu.cs.neptune.service.impl;

import edu.miu.cs.neptune.constant.Constant;
import edu.miu.cs.neptune.domain.ProfileVerificationType;
import edu.miu.cs.neptune.domain.User;
import edu.miu.cs.neptune.domain.UserVerification;
import edu.miu.cs.neptune.domain.UserVerificationType;
import edu.miu.cs.neptune.repository.UserRepository;
import edu.miu.cs.neptune.service.GenerateService;
import edu.miu.cs.neptune.service.MailService;
import edu.miu.cs.neptune.service.UserService;
import edu.miu.cs.neptune.service.UserVerificationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final MailService mailService;
  private final GenerateService generateService;

  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
                         MailService mailService, GenerateService generateService
  ) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.mailService = mailService;
    this.generateService = generateService;
  }

  @Override
  public User saveUser(User user) {
    if (user.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    generateVerificationCode(user);
    String mailSubject = "New Account notification";
    sendVerificationCode(mailSubject,user);
    User userNew = userRepository.save(user);
    userRepository.flush();
    return userNew;
  }

  @Override
  public User updateUser(User user) {
    User userNew = userRepository.save(user);
    userRepository.flush();
    return userNew;
  }

  @Override
  public User updatePassword(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
//    generateVerificationCode(user);
    User userNew = userRepository.save(user);
    String mailSubject = "Password reseted notification.";
//    sendVerificationCode(mailSubject,user);
    userRepository.flush();
    return userNew;
  }

  @Override
  public User forgotPasswordSendVC(User user) {
    generateVerificationCode(user);
    User userNew = userRepository.save(user);
    String mailSubject = "Forgot Password verification code";
    sendVerificationCode(mailSubject,user);
    userRepository.flush();
    return userNew;
  }

  public void generateVerificationCode(User user){
    String verificationCode = generateService.generateCode();
    user.setUserVerificationType(UserVerificationType.NEED_TO_VERIFY);
    user.resetFailedVerificationCount();
    user.setVerificationCode(verificationCode);
    user.setVerificationCreationTime(LocalDateTime.now().plusMinutes(Constant.EXPIRE_MINUTE));
  }
  public void sendVerificationCode(String mailSubject, User user){
    String mailFrom = "asdproject287@gmail.com";
    String mailContent = "Please use this verification code: "+user.getVerificationCode()+" to verify. \n Go to this link to login: http://localhost:9999/login";
    mailService.sendEmail(mailFrom,user.getEmail(),mailSubject,mailContent);
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public Optional<User> getById(Long userId) {
    return userRepository.findById(userId);
  }

  @Override
  public Optional<User> getByName(String username) {
    return userRepository.findByUsername(username);
  }

  //handle verify user's profile
  public List<User> findAllPendingProfile() {
    return userRepository.findAllPendingProfile();
  }
//  @Override
//  public void update(User user) {
//  }

//  @Override
//  public void inactive(String username) {
//  }

}
