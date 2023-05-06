package com.api.voterz.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private ElectionType electionType;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Candidate> candidates;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private  List<Voter> voters;
    private int year;
}
