package com.mike.M295_TrainTrack_Backend.training;

import com.mike.M295_TrainTrack_Backend.base.MessageResponse;
import com.mike.M295_TrainTrack_Backend.storage.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {
    private final TrainingRepository repository;

    public TrainingService(TrainingRepository repository) {
        this.repository = repository;
    }

    public List<Training> getTrainings() {return repository.findByOrderByNameAsc();}
    public Training getTraining(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Training.class));
    }

    public Training insertTraining(Training training) {return repository.save(training);}

    public Training updateTraining(Training training, Long id){
        return repository.findById(id)
                .map(trainingOrig -> {
                    trainingOrig.setName(training.getName());
                    trainingOrig.setExercises(training.getExercises());
                    trainingOrig.setGym(training.getGym());
                    return repository.save(trainingOrig);
                })
                .orElseGet(() -> repository.save(training));
    }

    public MessageResponse deleteTraining(Long id){
        repository.deleteById(id);
        return new MessageResponse("Training " + id + " deleted");
    }
}
