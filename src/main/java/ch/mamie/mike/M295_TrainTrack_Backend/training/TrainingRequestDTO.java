package ch.mamie.mike.M295_TrainTrack_Backend.training;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class TrainingRequestDTO {
    @NotEmpty
    private String name;

    @NotEmpty
    private String day;

    private Long gymId;
    private Set<Long> exerciseIds;
}