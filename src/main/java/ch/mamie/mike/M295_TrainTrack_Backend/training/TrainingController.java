package ch.mamie.mike.M295_TrainTrack_Backend.training;

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
public class TrainingController {

    private final TrainingService trainingService;

    TrainingController(TrainingService trainingService) { this.trainingService = trainingService; }

    @Tag(name = "Training", description = "Get trainings")
    @Operation(summary = "Get all trainings", description = "Returns a list of all trainings")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Trainings found",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Training.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping("api/training")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Training>> all() {
        List<Training> result = trainingService.getTrainings();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Tag(name = "Training")
    @Operation(summary = "Get a training by ID", description = "Returns a single training by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Training found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Training.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Training not found", content = @Content)
    })
    @GetMapping("api/training/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Training> one(
            @Parameter(description = "ID of the training to be retrieved", required = true, example = "1")
            @PathVariable Long id
    ) {
        Training training = trainingService.getTraining(id);
        return new ResponseEntity<>(training, HttpStatus.OK);
    }

    @Tag(name = "Training")
    @Operation(summary = "Create a new training", description = "Creates a new training and saves it to the database")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Training created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Training.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PostMapping("api/training")
    @RolesAllowed(Roles.Update)
    public ResponseEntity<Training> newTraining(
            @Parameter(description = "Training request object to be created", required = true)
            @Valid @RequestBody TrainingRequestDTO dto
    ) {
        Training savedTraining = trainingService.insertTraining(dto);
        return new ResponseEntity<>(savedTraining, HttpStatus.OK);
    }

    @Tag(name = "Training")
    @Operation(summary = "Update a training", description = "Updates an existing training by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Training updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Training.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Training not found", content = @Content)
    })
    @PutMapping("api/training/{id}")
    @RolesAllowed(Roles.Update)
    public ResponseEntity<Training> updateTraining(
            @Parameter(description = "Updated training object", required = true)
            @Valid @RequestBody Training training,
            @Parameter(description = "ID of the training to be updated", required = true, example = "1")
            @PathVariable Long id
    ) {
        Training savedTraining = trainingService.updateTraining(training, id);
        return new ResponseEntity<>(savedTraining, HttpStatus.OK);
    }

    @Tag(name = "Training")
    @Operation(summary = "Delete a training", description = "Deletes a training by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Training deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Training not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("api/training/{id}")
    @RolesAllowed(Roles.Update)
    public ResponseEntity<MessageResponse> deleteTraining(
            @Parameter(description = "ID of the training to be deleted", required = true, example = "1")
            @PathVariable Long id
    ) {
        try {
            return ResponseEntity.ok(trainingService.deleteTraining(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}