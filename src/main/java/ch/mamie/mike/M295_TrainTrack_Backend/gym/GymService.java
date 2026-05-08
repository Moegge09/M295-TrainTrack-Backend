package ch.mamie.mike.M295_TrainTrack_Backend.gym;

import ch.mamie.mike.M295_TrainTrack_Backend.base.MessageResponse;
import ch.mamie.mike.M295_TrainTrack_Backend.storage.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymService {
    private final GymRepository repository;

    public GymService(GymRepository repository) {this.repository = repository;}

    public List<Gym> getGyms(){
        return repository.findByOrderByNameAsc();
    }

    public Gym getGym(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Gym.class));
    }

    public Gym insertGym(Gym gym) {return repository.save(gym);}

    public Gym updateGym(Gym gym, Long id) {
        return repository.findById(id)
                .map( gymOrig -> {
                    gymOrig.setName(gym.getName());
                    gymOrig.setAddress(gym.getAddress());
                    return repository.save(gymOrig);
                })
                .orElseGet(() -> repository.save(gym));
    }

    public MessageResponse deleteGym(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Gym " + id + " deleted");
    }
}
