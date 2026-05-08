package ch.mamie.mike.M295_TrainTrack_Backend.gym;

import ch.mamie.mike.M295_TrainTrack_Backend.base.MessageResponse;
import ch.mamie.mike.M295_TrainTrack_Backend.security.Roles;
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
public class GymController {
    private final GymService gymService;

    GymController(GymService gymService) {this.gymService = gymService;}

    @GetMapping("api/gym")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Gym>> all() {
        List<Gym> result = gymService.getGyms();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/gym/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Gym> one(@PathVariable Long id){
        Gym gym = gymService.getGym(id);
        return new ResponseEntity<>(gym, HttpStatus.OK);
    }

    @PostMapping("api/gym")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Gym> newGym(@Valid @RequestBody Gym gym){
        Gym savedGym = gymService.insertGym(gym);
        return new ResponseEntity<>(savedGym, HttpStatus.OK);
    }

    @PutMapping("api/gym/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Gym> updateGym(@Valid @RequestBody Gym gym, @PathVariable Long id) {
        Gym savedGym = gymService.updateGym(gym, id);
        return new ResponseEntity<>(savedGym, HttpStatus.OK);
    }

    @DeleteMapping("api/gym/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteGym(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(gymService.deleteGym(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
