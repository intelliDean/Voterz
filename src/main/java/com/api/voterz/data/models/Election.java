package com.api.voterz.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(timezone = "EEEE, dd MMMM yyyy HH:mm:ss")
    private LocalDateTime electionSchedule;

}
