package ch.mamie.mike.M295_TrainTrack_Backend.exercise;

import ch.mamie.mike.M295_TrainTrack_Backend.base.MessageResponse;
import ch.mamie.mike.M295_TrainTrack_Backend.security.Roles;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "Exercise", description = "Get exercises")
    @Operation(summary = "Get all exercises", description = "Returns a list of all exercises")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Exercises found",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Exercise.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping("api/exercise")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Exercise>> all() {
        List<Exercise> result = exerciseService.getExercise();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Tag(name = "Exercise")
    @Operation(summary = "Get an exercise by ID", description = "Returns a single exercise by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Exercise found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Exercise.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Exercise not found", content = @Content)
    })
    @GetMapping("api/exercise/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Exercise> one(
            @Parameter(description = "ID of the exercise to be retrieved", required = true, example = "1")
            @PathVariable Long id
    ) {
        Exercise exercise = exerciseService.getExercise(id);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @Tag(name = "Exercise")
    @Operation(summary = "Create a new exercise", description = "Creates a new exercise and saves it to the database")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Exercise created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Exercise.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PostMapping("api/exercise")
    @RolesAllowed(Roles.Update)
    public ResponseEntity<Exercise> newExercise(
            @Parameter(description = "Exercise object to be created", required = true)
            @Valid @RequestBody Exercise exercise
    ) {
        Exercise savedExercise = exerciseService.insertExercise(exercise);
        return new ResponseEntity<>(savedExercise, HttpStatus.OK);
    }

    @Tag(name = "Exercise")
    @Operation(summary = "Update an exercise", description = "Updates an existing exercise by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Exercise updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Exercise.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Exercise not found", content = @Content)
    })
    @PutMapping("api/exercise/{id}")
    @RolesAllowed(Roles.Update)
    public ResponseEntity<Exercise> updateExercise(
            @Parameter(description = "Updated exercise object", required = true)
            @Valid @RequestBody Exercise exercise,
            @Parameter(description = "ID of the exercise to be updated", required = true, example = "1")
            @PathVariable Long id
    ) {
        Exercise savedExercise = exerciseService.updateExercise(exercise, id);
        return new ResponseEntity<>(savedExercise, HttpStatus.OK);
    }

    @Tag(name = "Exercise")
    @Operation(summary = "Delete an exercise", description = "Deletes an exercise by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Exercise deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Exercise not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("api/exercise/{id}")
    @RolesAllowed(Roles.Update)
    public ResponseEntity<MessageResponse> deleteExercise(
            @Parameter(description = "ID of the exercise to be deleted", required = true, example = "1")
            @PathVariable Long id
    ) {
        try {
            return ResponseEntity.ok(exerciseService.deleteExercise(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}