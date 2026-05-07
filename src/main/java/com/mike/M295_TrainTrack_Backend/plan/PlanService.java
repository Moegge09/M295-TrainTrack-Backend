package com.mike.M295_TrainTrack_Backend.plan;

import com.mike.M295_TrainTrack_Backend.base.MessageResponse;
import com.mike.M295_TrainTrack_Backend.storage.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {
    private final PlanRepository repository;

    public PlanService(PlanRepository repository) {this.repository = repository;}

    public List<Plan> getPlans() {return repository.findByOrderByNameAsc();}

    public Plan getPlan(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Plan.class));
    }

    public Plan insertPlan(Plan plan) {return repository.save(plan);}

    public Plan updatePlan(Plan plan, Long id) {
        return repository.findById(id)
                .map( mapOrig -> {
                    mapOrig.setName(plan.getName());
                    mapOrig.setTrainings(plan.getTrainings());
                    return repository.save(mapOrig);
                })
                .orElseGet(() -> repository.save(plan));
    }

    public MessageResponse deletePlan(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Plan " + id + " deleted");
    }
}
