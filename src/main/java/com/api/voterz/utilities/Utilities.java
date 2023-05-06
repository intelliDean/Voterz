package com.api.voterz.utilities;

import com.api.voterz.exceptions.VoterzException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Utilities {
    public static int calculateAge(String dateOfBirth) {
        DateTimeFormatter formatter;
        try {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        } catch (VoterzException e) {
            throw new VoterzException("invalid date");
        }
        LocalDate dob = LocalDate.parse(dateOfBirth, formatter);
        LocalDate currentDate = LocalDate.now();

        Period period = Period.between(dob, currentDate);
        return period.getYears();
    }

}
