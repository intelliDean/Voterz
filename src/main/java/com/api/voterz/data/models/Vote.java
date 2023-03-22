package com.api.voterz.data.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private Voter voter;
    @ManyToOne( fetch = FetchType.LAZY)
    private Candidate candidate;
    private String timeCasted;
    private boolean casted;
    @Enumerated(EnumType.STRING)
    private ElectionType type;
}
