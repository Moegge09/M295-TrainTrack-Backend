package ch.mamie.mike.M295_TrainTrack_Backend.plan;

import ch.mamie.mike.M295_TrainTrack_Backend.training.Training;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    private String name;

    @ManyToMany
    @JoinTable(name = "training_plan",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "training_id"))
    private Set<Training> trainings;

    public Plan() {
    }
}
