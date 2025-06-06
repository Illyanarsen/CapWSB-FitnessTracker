package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import pl.wsb.fitnesstracker.training.api.TrainingUpdateDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Serwis implementujący logikę biznesową dla zarządzania treningami użytkowników.
 * Odpowiada za operacje CRUD oraz mapowanie między encjami i DTO.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final UserService userService;
    private final TrainingMapper trainingMapper;

    /**
     * Metoda jeszcze niezaimplementowana – zgodna z interfejsem {@link TrainingProvider}.
     *
     * @param trainingId identyfikator treningu
     * @return wyjątek {@link UnsupportedOperationException}
     */
    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    /**
     * Zwraca wszystkie treningi znajdujące się w systemie.
     *
     * @return lista wszystkich treningów
     */
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    /**
     * Zwraca treningi, których czas zakończenia przypada po wskazanej dacie.
     *
     * @param aftertime data graniczna
     * @return lista treningów zakończonych po {@code aftertime}
     */
    public List<Training> findAllTrainingsByTime(Date aftertime) {
        return trainingRepository.findByTime(aftertime);
    }

    /**
     * Zwraca treningi przypisane do użytkownika o podanym identyfikatorze.
     *
     * @param id identyfikator użytkownika
     * @return lista treningów użytkownika
     */
    public List<Training> findAllTrainingsById(Long id) {
        return trainingRepository.findByUserId(id);
    }

    /**
     * Zwraca treningi o określonym typie aktywności.
     *
     * @param activityType typ aktywności (np. RUNNING, SWIMMING)
     * @return lista treningów danego typu
     */
    public List<Training> findAllTrainingsByActivity(ActivityType activityType) {
        return trainingRepository.findByActivity(activityType);
    }

    /**
     * Aktualizuje istniejący trening na podstawie przesłanego DTO.
     *
     * @param id  identyfikator treningu do aktualizacji
     * @param dto dane aktualizujące trening
     * @return zaktualizowany trening jako DTO
     * @throws ResponseStatusException jeśli trening lub użytkownik nie zostanie odnaleziony
     */
    public TrainingDto updateTraining(Long id, TrainingUpdateDto dto) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Training not found"));

        User user = userService.findUserByID(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        training.update(
                user,
                dto.endTime(),
                dto.startTime(),
                dto.activityType(),
                dto.distance(),
                dto.averageSpeed()
        );

        return trainingMapper.toDto(trainingRepository.save(training));
    }

    /**
     * Zapisuje nowy trening w repozytorium.
     *
     * @param training trening do zapisania
     * @return zapisany trening
     */
    public Training saveTraining(Training training) {
        return trainingRepository.save(training);
    }
}
