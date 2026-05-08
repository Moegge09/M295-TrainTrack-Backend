package ch.mamie.mike.M295_TrainTrack_Backend.plan;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class PlanRequestDTO {
    private String name;

    private Set<Long> trainingIds;
}
