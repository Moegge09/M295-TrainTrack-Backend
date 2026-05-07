package com.mike.M295_TrainTrack_Backend.exercise;

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
public class ExerciseController {
    private final ExerciseService exerciseService;

    ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("api/exercise")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Exercise>> all() {
        List<Exercise> result = exerciseService.getExercise();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/exercise/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Exercise> one(@PathVariable Long id) {
        Exercise exercise = exerciseService.getExercise(id);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @PostMapping("api/exercise")
    @RolesAllowed(Roles.Update)
    public ResponseEntity<Exercise> newExercise(@Valid @RequestBody Exercise exercise) {
        Exercise savedExercise = exerciseService.insertExercise(exercise);
        return new ResponseEntity<>(savedExercise, HttpStatus.OK);
    }

    @PutMapping("api/exercise/{id}")
    @RolesAllowed(Roles.Update)
    public ResponseEntity<Exercise> updateExercise(@Valid @RequestBody Exercise exercise, @PathVariable Long id){
        Exercise savedExercise = exerciseService.updateExercise(exercise, id);
        return new ResponseEntity<>(savedExercise, HttpStatus.OK);
    }

    @DeleteMapping("api/exercise/{id}")
    @RolesAllowed(Roles.Update)
    public ResponseEntity<MessageResponse> deleteExercise(@PathVariable Long id){
        try {
            return ResponseEntity.ok(exerciseService.deleteExercise(id));
        } catch (Throwable t){
            return ResponseEntity.internalServerError().build();
        }
    }
}
