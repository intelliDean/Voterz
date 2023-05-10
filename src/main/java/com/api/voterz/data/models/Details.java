package com.api.voterz.data.models;

import jakarta.persistence.*;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String image;
    private Integer age;
    @Column(columnDefinition = "ENUM('Yes', 'No')")
    private Boolean registered;
    @Enumerated(EnumType.STRING)
    private LG lg;
    @Enumerated(EnumType.STRING)
    private Constituency constituency;
    @Enumerated(EnumType.STRING)
    private State state;
}
