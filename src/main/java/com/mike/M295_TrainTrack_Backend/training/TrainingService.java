package com.mike.M295_TrainTrack_Backend.training;

import com.mike.M295_TrainTrack_Backend.base.MessageResponse;
import com.mike.M295_TrainTrack_Backend.exercise.Exercise;
import com.mike.M295_TrainTrack_Backend.exercise.ExerciseRepository;
import com.mike.M295_TrainTrack_Backend.gym.Gym;
import com.mike.M295_TrainTrack_Backend.gym.GymRepository;
import com.mike.M295_TrainTrack_Backend.storage.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TrainingService {
    private final TrainingRepository repository;
    private final ExerciseRepository exerciseRepository;
    private final GymRepository gymRepository;

    public TrainingService(TrainingRepository repository, ExerciseRepository exerciseRepository, GymRepository gymRepository) {
        this.repository = repository;
        this.exerciseRepository = exerciseRepository;
        this.gymRepository = gymRepository;
    }

    public List<Training> getTrainings() {return repository.findByOrderByNameAsc();}
    public Training getTraining(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Training.class));
    }

    public Training insertTraining(TrainingRequestDTO dto) {
        Training training = new Training();
        training.setName(dto.getName());
        training.setDay(dto.getDay());

        if (dto.getExerciseIds() != null) {
            Set<Exercise> exercises = new HashSet<>(exerciseRepository.findAllById(dto.getExerciseIds()));
            training.setExercises(exercises);
        }

        if (dto.getGymId() != null) {
            Gym gym = gymRepository.findById(dto.getGymId())
                    .orElseThrow(() -> new EntityNotFoundException(dto.getGymId(), Gym.class));
            training.setGym(gym);
        }

        return repository.save(training);
    }

    public Training updateTraining(Training training, Long id){
        return repository.findById(id)
                .map(trainingOrig -> {
                    trainingOrig.setName(training.getName());
                    trainingOrig.setExercises(training.getExercises());
                    trainingOrig.setGym(training.getGym());
                    trainingOrig.setDay(training.getDay());
                    return repository.save(trainingOrig);
                })
                .orElseGet(() -> repository.save(training));
    }

    public MessageResponse deleteTraining(Long id){
        repository.deleteById(id);
        return new MessageResponse("Training " + id + " deleted");
    }
}
