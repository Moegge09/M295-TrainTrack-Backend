package ch.mamie.mike.M295_TrainTrack_Backend.plan;

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
public class PlanController {

    private final PlanService planService;

    PlanController(PlanService planService) { this.planService = planService; }

    @Tag(name = "Plan", description = "Get plans")
    @Operation(summary = "Get all plans", description = "Returns a list of all training plans")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Plans found",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Plan.class))
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping("api/plan")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Plan>> all() {
        List<Plan> result = planService.getPlans();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Tag(name = "Plan")
    @Operation(summary = "Get a plan by ID", description = "Returns a single training plan by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Plan found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Plan.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Plan not found", content = @Content)
    })
    @GetMapping("api/plan/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Plan> one(
            @Parameter(description = "ID of the plan to be retrieved", required = true, example = "1")
            @PathVariable Long id
    ) {
        Plan plan = planService.getPlan(id);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @Tag(name = "Plan")
    @Operation(summary = "Create a new plan", description = "Creates a new training plan and saves it to the database")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Plan created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Plan.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PostMapping("api/plan")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Plan> newPlan(
            @Parameter(description = "Plan request object to be created", required = true)
            @Valid @RequestBody PlanRequestDTO dto
    ) {
        Plan savedPlan = planService.insertPlan(dto);
        return new ResponseEntity<>(savedPlan, HttpStatus.OK);
    }

    @Tag(name = "Plan")
    @Operation(summary = "Update a plan", description = "Updates an existing training plan by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Plan updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Plan.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Plan not found", content = @Content)
    })
    @PutMapping("api/plan/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Plan> updatePlan(
            @Parameter(description = "Updated plan request object", required = true)
            @Valid
            @RequestBody PlanRequestDTO dto,
            @Parameter(description = "ID of the plan to be updated", required = true, example = "1")
            @PathVariable Long id
    ) {
        Plan savedPlan = planService.updatePlan(dto, id);
        return new ResponseEntity<>(savedPlan, HttpStatus.OK);
    }

    @Tag(name = "Plan")
    @Operation(summary = "Delete a plan", description = "Deletes a training plan by its ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Plan deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Plan not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("api/plan/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deletePlan(
            @Parameter(description = "ID of the plan to be deleted", required = true, example = "1")
            @PathVariable Long id
    ) {
        try {
            return ResponseEntity.ok(planService.deletePlan(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}