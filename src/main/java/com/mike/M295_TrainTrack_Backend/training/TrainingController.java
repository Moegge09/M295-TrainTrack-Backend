package com.mike.M295_TrainTrack_Backend.training;

import com.mike.M295_TrainTrack_Backend.base.MessageResponse;
import com.mike.M295_TrainTrack_Backend.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
public class TrainingController {
    private final TrainingService trainingService;

    TrainingController(TrainingService trainingService) {this.trainingService = trainingService;}

    @GetMapping("api/training")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Training>> all() {
        List<Training> result = trainingService.getTrainings();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/training/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Training> one(@PathVariable Long id){
        Training training = trainingService.getTraining(id);
        return  new ResponseEntity<>(training, HttpStatus.OK);
    }

    @PostMapping("api/training")
    @RolesAllowed(Roles.Update)
    public ResponseEntity<Training> newTraining(@Valid @RequestBody Training training){
        Training savedTraining = trainingService.insertTraining(training);
        return new ResponseEntity<>(savedTraining, HttpStatus.OK);
    }

    @PutMapping("api/training/{id}")
    @RolesAllowed(Roles.Update)
    public ResponseEntity<Training> updateTraining(@Valid @RequestBody Training training, @PathVariable Long id) {
        Training savedTraining = trainingService.updateTraining(training, id);
        return new ResponseEntity<>(savedTraining, HttpStatus.OK);
    }

    @DeleteMapping("api/training/{id}")
    @RolesAllowed(Roles.Update)
    public ResponseEntity<MessageResponse> deleteTraining(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(trainingService.deleteTraining(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
