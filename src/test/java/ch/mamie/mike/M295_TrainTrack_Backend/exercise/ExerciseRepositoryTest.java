package ch.mamie.mike.M295_TrainTrack_Backend.exercise;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ExerciseRepositoryTest {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Test
    void savePersistsExercise() {
        Exercise exercise = createExercise("Bench Press", 80.0);

        Exercise savedExercise = exerciseRepository.save(exercise);
        Optional<Exercise> reloadedExercise = exerciseRepository.findById(savedExercise.getId());

        assertTrue(savedExercise.getId() != null);
        assertTrue(reloadedExercise.isPresent());
        assertEquals("Bench Press", reloadedExercise.get().getName());
        assertEquals(80.0, reloadedExercise.get().getWeight());
    }

    @Test
    void findByOrderByNameAscReturnsExercisesSortedByName() {
        exerciseRepository.save(createExercise("Squat", 120.0));
        exerciseRepository.save(createExercise("Bench Press", 80.0));
        exerciseRepository.save(createExercise("Deadlift", 140.0));

        List<Exercise> exercises = exerciseRepository.findByOrderByNameAsc();

        assertEquals(3, exercises.size());
        assertEquals("Bench Press", exercises.get(0).getName());
        assertEquals("Deadlift", exercises.get(1).getName());
        assertEquals("Squat", exercises.get(2).getName());
    }

    private Exercise createExercise(String name, Double weight) {
        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercise.setWeight(weight);
        return exercise;
    }
}
