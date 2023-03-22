package com.api.voterz.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.persistence.Column;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Party party;
    private int age;
    private String candidateImage;
    @Column(columnDefinition = "ENUM('Yes', 'No')")
    private boolean registered;
    private Long numberOfVotes;
    @Enumerated(EnumType.STRING)
    private ElectionType ElectionType;
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Vote> votes;
}
