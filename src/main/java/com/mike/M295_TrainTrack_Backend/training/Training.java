package com.mike.M295_TrainTrack_Backend.training;

import com.mike.M295_TrainTrack_Backend.exercise.Exercise;
import com.mike.M295_TrainTrack_Backend.gym.Gym;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    private String name;

    @ManyToMany
    @JoinTable(name = "training_exercise",
        joinColumns = @JoinColumn(name = "training_id"),
        inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    private Set<Exercise> exercises;

    @ManyToOne
    @JoinTable(name = "gym_id")
    private Gym gym;

    public Training() {
    }
}
