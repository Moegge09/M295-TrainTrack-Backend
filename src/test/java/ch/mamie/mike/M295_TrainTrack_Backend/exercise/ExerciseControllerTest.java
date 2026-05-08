package ch.mamie.mike.M295_TrainTrack_Backend.exercise;

import ch.mamie.mike.M295_TrainTrack_Backend.base.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExerciseControllerTest {

    @Mock
    private ExerciseService exerciseService;

    private ExerciseController exerciseController;

    @BeforeEach
    void setUp() {
        exerciseController = new ExerciseController(exerciseService);
    }

    @Test
    void allReturnsExercisesWithOkStatus() {
        List<Exercise> exercises = List.of(createExercise(1L, "Bench Press", 80.0));
        when(exerciseService.getExercise()).thenReturn(exercises);

        ResponseEntity<List<Exercise>> response = exerciseController.all();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(exercises, response.getBody());
        verify(exerciseService).getExercise();
    }

    @Test
    void oneReturnsExerciseWithOkStatus() {
        Exercise exercise = createExercise(3L, "Squat", 120.0);
        when(exerciseService.getExercise(3L)).thenReturn(exercise);

        ResponseEntity<Exercise> response = exerciseController.one(3L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(exercise, response.getBody());
        verify(exerciseService).getExercise(3L);
    }

    @Test
    void newExerciseReturnsSavedExerciseWithOkStatus() {
        Exercise exercise = createExercise(null, "Deadlift", 140.0);
        Exercise savedExercise = createExercise(4L, "Deadlift", 140.0);
        when(exerciseService.insertExercise(exercise)).thenReturn(savedExercise);

        ResponseEntity<Exercise> response = exerciseController.newExercise(exercise);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedExercise, response.getBody());
        verify(exerciseService).insertExercise(exercise);
    }

    @Test
    void updateExerciseReturnsUpdatedExerciseWithOkStatus() {
        Exercise exercise = createExercise(null, "Row", 65.0);
        Exercise updatedExercise = createExercise(5L, "Row", 65.0);
        when(exerciseService.updateExercise(exercise, 5L)).thenReturn(updatedExercise);

        ResponseEntity<Exercise> response = exerciseController.updateExercise(exercise, 5L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedExercise, response.getBody());
        verify(exerciseService).updateExercise(exercise, 5L);
    }

    @Test
    void deleteExerciseReturnsMessageResponseWithOkStatus() {
        MessageResponse messageResponse = new MessageResponse("Exercise 6 deleted");
        when(exerciseService.deleteExercise(6L)).thenReturn(messageResponse);

        ResponseEntity<MessageResponse> response = exerciseController.deleteExercise(6L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messageResponse.getMessage(), response.getBody().getMessage());
        verify(exerciseService).deleteExercise(6L);
    }

    @Test
    void deleteExerciseReturnsInternalServerErrorWhenServiceThrows() {
        when(exerciseService.deleteExercise(7L)).thenThrow(new RuntimeException("delete failed"));

        ResponseEntity<MessageResponse> response = exerciseController.deleteExercise(7L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(exerciseService).deleteExercise(7L);
    }

    private Exercise createExercise(Long id, String name, Double weight) {
        Exercise exercise = new Exercise();
        exercise.setId(id);
        exercise.setName(name);
        exercise.setWeight(weight);
        return exercise;
    }
}
