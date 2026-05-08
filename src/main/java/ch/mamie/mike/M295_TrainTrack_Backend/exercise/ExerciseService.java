package ch.mamie.mike.M295_TrainTrack_Backend.exercise;

import ch.mamie.mike.M295_TrainTrack_Backend.base.MessageResponse;
import ch.mamie.mike.M295_TrainTrack_Backend.storage.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {
    private final ExerciseRepository repository;

    public ExerciseService(ExerciseRepository repository) {this.repository = repository;}

    public List<Exercise> getExercise() {return repository.findByOrderByNameAsc();}

    public Exercise getExercise(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Exercise.class));
    }

    public Exercise insertExercise(Exercise exercise) {
        return repository.save(exercise);
    }

    public Exercise updateExercise(Exercise exercise, Long id){
        return repository.findById(id)
                .map(exerciseOrig -> {
                    exerciseOrig.setName(exercise.getName());
                    exerciseOrig.setWeight(exercise.getWeight());
                    return repository.save(exerciseOrig);
                })
                .orElseGet(() -> repository.save(exercise));
    }

    public MessageResponse deleteExercise(Long id){
        repository.deleteById(id);
        return new MessageResponse("Exercise " + id + " deleted");
    }
}
