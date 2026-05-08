package com.mike.M295_TrainTrack_Backend.plan;

import com.mike.M295_TrainTrack_Backend.base.MessageResponse;
import com.mike.M295_TrainTrack_Backend.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.aspectj.bridge.Message;
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

    PlanController(PlanService planService) {this.planService = planService;}

    @GetMapping("api/plan")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Plan>> all() {
        List<Plan> result = planService.getPlans();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/plan/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Plan> one(@PathVariable Long id){
        Plan plan = planService.getPlan(id);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @PostMapping("api/plan")
    public ResponseEntity<Plan> newPlan(@Valid @RequestBody PlanRequestDTO dto) {
        Plan savedPlan = planService.insertPlan(dto);
        return new ResponseEntity<>(savedPlan, HttpStatus.OK);
    }

    @PutMapping("api/plan/{id}")
    public ResponseEntity<Plan> updatePlan(@RequestBody PlanRequestDTO dto, @PathVariable Long id) {
        Plan savedPlan = planService.updatePlan(dto, id);
        return new ResponseEntity<>(savedPlan, HttpStatus.OK);
    }

    @DeleteMapping("api/plan/{id}")
    @RolesAllowed(Roles.Update)
    public ResponseEntity<MessageResponse> deletePlan(@PathVariable Long id){
        try {
            return ResponseEntity.ok(planService.deletePlan(id));
        } catch (Throwable t){
            return ResponseEntity.internalServerError().build();
        }
    }
}
