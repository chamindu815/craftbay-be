package com.craftbay.crafts.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private int id;
    private String username;
    private String role;
    private String firstName;
    private String lastName;

    private String houseNo;
    private String streetName;
    private String city;
    private String country;
    private String phoneNo;
    private LocalDate dateOfBirth;
}
