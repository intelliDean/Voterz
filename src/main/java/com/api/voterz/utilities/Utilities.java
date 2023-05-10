package com.api.voterz.utilities;

import com.api.voterz.exceptions.VoterzException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.TextCodec;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.stream.Collectors;

public class Utilities {
    public static final int MAX_PER_PAGE = 5;
    public static final String CANDIDATE_MAIL = "C:\\Users\\Dean\\IdeaProjects\\voterz\\src\\main\\resources\\candidate_mail.txt";
    public static final String USER_VERIFICATION_BASE_URL = "localhost:9090/api/v1/user/account/verify";

    private JwtUtil util;

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
    public static String getCandidateMail() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CANDIDATE_MAIL))) {
            return reader.lines().collect(Collectors.joining());
        } catch (IOException exception) {
            throw new VoterzException(exception.getMessage());
        }
    }

    public static String generateVerificationLink(Long userId) {
        return USER_VERIFICATION_BASE_URL + "?userId=" + userId + "&token=" + generateVerificationToken();
    }

    private static String generateVerificationToken() {
        Date expiration = Date.from(Instant.now().plusSeconds(86400));
        return Jwts.builder()
                .setIssuer("DMail")
                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E="))
                .setExpiration(expiration)
                .setIssuedAt(Date.from(Instant.now()))
                .compact();
    }

    public static boolean isTokenSigned(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E="))
                    .parseClaimsJws(token);
            return true; // Token is signed
        } catch (SignatureException e) {
            return false; //unsigned token
        } catch (JwtException e) {
            return false; // invalid token
        }
    }
}
