package com.mike.M295_TrainTrack_Backend.exercises;

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
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Exercise> newExercise(@Valid @RequestBody Exercise exercise) {
        Exercise savedExercise = exerciseService.insertExercise(exercise);
        return new ResponseEntity<>(savedExercise, HttpStatus.OK);
    }
}
