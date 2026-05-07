package com.mike.M295_TrainTrack_Backend.gym;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymRepository extends JpaRepository<Gym, Long> {
    List<Gym> findByOrderByNameAsc();
}
