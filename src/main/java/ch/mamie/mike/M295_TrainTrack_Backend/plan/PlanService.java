package ch.mamie.mike.M295_TrainTrack_Backend.plan;

import ch.mamie.mike.M295_TrainTrack_Backend.base.MessageResponse;
import ch.mamie.mike.M295_TrainTrack_Backend.storage.EntityNotFoundException;
import ch.mamie.mike.M295_TrainTrack_Backend.training.Training;
import ch.mamie.mike.M295_TrainTrack_Backend.training.TrainingRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlanService {
    private final PlanRepository repository;
    private final TrainingRepository trainingRepository;

    public PlanService(PlanRepository repository, TrainingRepository trainingRepository) {
        this.repository = repository;
        this.trainingRepository = trainingRepository;
    }

    public List<Plan> getPlans() {return repository.findByOrderByNameAsc();}

    public Plan getPlan(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Plan.class));
    }

    public Plan insertPlan(PlanRequestDTO dto) {
        Plan plan = new Plan();
        plan.setName(dto.getName());

        if (dto.getTrainingIds() != null) {
            Set<Training> trainings = new HashSet<>(trainingRepository.findAllById(dto.getTrainingIds()));
            plan.setTrainings(trainings);
        }

        return repository.save(plan);
    }

    public Plan updatePlan(PlanRequestDTO dto, Long id) {
        return repository.findById(id)
                .map(planOrig -> {
                    if (dto.getName() != null) planOrig.setName(dto.getName());
                    if (dto.getTrainingIds() != null) {
                        Set<Training> trainings = new HashSet<>(trainingRepository.findAllById(dto.getTrainingIds()));
                        planOrig.setTrainings(trainings);
                    }
                    return repository.save(planOrig);
                })
                .orElseThrow(() -> new EntityNotFoundException(id, Plan.class));
    }

    public MessageResponse deletePlan(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Plan " + id + " deleted");
    }
}
