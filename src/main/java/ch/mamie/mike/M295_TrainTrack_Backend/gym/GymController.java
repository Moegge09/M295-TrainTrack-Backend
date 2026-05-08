package ch.mamie.mike.M295_TrainTrack_Backend.gym;

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
public class GymController {

    private final GymService gymService;

    GymController(GymService gymService) { this.gymService = gymService; }

    @Tag(name = "Gym", description = "Get gyms")
    @Operation(summary = "Get all gyms", description = "Returns a list of all gyms")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Gyms found",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Gym.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping("api/gym")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Gym>> all() {
        List<Gym> result = gymService.getGyms();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Tag(name = "Gym")
    @Operation(summary = "Get a gym by ID", description = "Returns a single gym by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Gym found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Gym.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Gym not found", content = @Content)
    })
    @GetMapping("api/gym/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Gym> one(
            @Parameter(description = "ID of the gym to be retrieved", required = true, example = "1")
            @PathVariable Long id
    ) {
        Gym gym = gymService.getGym(id);
        return new ResponseEntity<>(gym, HttpStatus.OK);
    }

    @Tag(name = "Gym")
    @Operation(summary = "Create a new gym", description = "Creates a new gym and saves it to the database")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Gym created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Gym.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PostMapping("api/gym")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Gym> newGym(
            @Parameter(description = "Gym object to be created", required = true)
            @Valid @RequestBody Gym gym
    ) {
        Gym savedGym = gymService.insertGym(gym);
        return new ResponseEntity<>(savedGym, HttpStatus.OK);
    }

    @Tag(name = "Gym")
    @Operation(summary = "Update a gym", description = "Updates an existing gym by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Gym updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Gym.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Gym not found", content = @Content)
    })
    @PutMapping("api/gym/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Gym> updateGym(
            @Parameter(description = "Updated gym object", required = true)
            @Valid @RequestBody Gym gym,
            @Parameter(description = "ID of the gym to be updated", required = true, example = "1")
            @PathVariable Long id
    ) {
        Gym savedGym = gymService.updateGym(gym, id);
        return new ResponseEntity<>(savedGym, HttpStatus.OK);
    }

    @Tag(name = "Gym")
    @Operation(summary = "Delete a gym", description = "Deletes a gym by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Gym deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Gym not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("api/gym/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteGym(
            @Parameter(description = "ID of the gym to be deleted", required = true, example = "1")
            @PathVariable Long id
    ) {
        try {
            return ResponseEntity.ok(gymService.deleteGym(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}