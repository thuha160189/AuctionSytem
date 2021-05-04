package edu.miu.cs.neptune.service.impl;

import edu.miu.cs.neptune.service.GenerateService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateServiceImpl implements GenerateService {
  //Alpha numeric String
  @Override
  public String generateCode() {
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 6;
    Random random = new Random();

    String generatedString = random.ints(leftLimit, rightLimit + 1)
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
    return generatedString;
  }

}
