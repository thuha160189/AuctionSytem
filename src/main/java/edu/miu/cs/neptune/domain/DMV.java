package edu.miu.cs.neptune.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class DMV {
    @Id
    private Long Id;
    private String email;
    private String firstName;
    private String lastName;
    private String licenseNumber;
}
