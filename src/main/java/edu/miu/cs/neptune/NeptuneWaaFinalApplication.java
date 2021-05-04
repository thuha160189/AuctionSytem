package edu.miu.cs.neptune;

import edu.miu.cs.neptune.controller.CustomerController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@EnableScheduling
public class NeptuneWaaFinalApplication {

    public static void main(String[] args) {

        SpringApplication.run(NeptuneWaaFinalApplication.class, args);
    }

}
