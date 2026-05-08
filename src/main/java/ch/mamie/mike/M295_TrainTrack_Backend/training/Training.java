package ch.mamie.mike.M295_TrainTrack_Backend.training;

import ch.mamie.mike.M295_TrainTrack_Backend.exercise.Exercise;
import ch.mamie.mike.M295_TrainTrack_Backend.gym.Gym;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;
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

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private DayOfWeek day;

    @ManyToMany
    @JoinTable(name = "training_exercise",
        joinColumns = @JoinColumn(name = "training_id"),
        inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    private Set<Exercise> exercises;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    public Training() {
    }
}
