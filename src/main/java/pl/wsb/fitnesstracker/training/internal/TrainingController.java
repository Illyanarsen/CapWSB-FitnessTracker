package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingCreateDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.training.api.TrainingUpdateDto;
import pl.wsb.fitnesstracker.user.api.UserService;


import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Kontroler REST dla operacji CRUD na treningach użytkowników systemu FitnessTracker.
 * Udostępnia endpointy do tworzenia, odczytywania, aktualizowania i usuwania treningów,
 * a także do ich wyszukiwania według różnych kryteriów.
 */
/**
 * Kontroler REST dla operacji CRUD na treningach użytkowników systemu FitnessTracker.
 * <p>
 * Udostępnia endpointy do:
 * <ul>
 *     <li>pobierania wszystkich treningów,</li>
 *     <li>pobierania treningów zakończonych po podanym czasie,</li>
 *     <li>pobierania treningów konkretnego użytkownika,</li>
 *     <li>filtrowania treningów według rodzaju aktywności,</li>
 *     <li>tworzenia nowego treningu,</li>
 *     <li>aktualizacji istniejącego treningu.</li>
 * </ul>
 * </p>
 * Ścieżki rozpoczynają się od: <code>/v1/trainings</code>
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor

public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;
    private final UserService userService;

    /**
     * Zwraca listę wszystkich treningów w systemie.
     *
     * @return lista obiektów {@link TrainingDto}
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Zwraca listę treningów zakończonych po określonym czasie.
     *
     * @param aftertime data, po której zakończone treningi mają być zwrócone
     * @return lista obiektów {@link TrainingDto}
     */
    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getDetailsAboutTrainingByTime  (@PathVariable("afterTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date aftertime) {
        return trainingService.findAllTrainingsByTime(aftertime)
                .stream()
                .map(trainingMapper::toDto)
                .toList();

    }

    /**
     * Zwraca wszystkie treningi przypisane do użytkownika o podanym ID.
     *
     * @param id identyfikator użytkownika
     * @return lista treningów danego użytkownika
     */
    @GetMapping("/{id}")
    public List<TrainingDto> getAllTrainingsByUserId(@PathVariable("id") Long id) {
        return trainingService.findAllTrainingsById(id)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Zwraca treningi według typu aktywności (np. bieganie, pływanie).
     *
     * @param activityType typ aktywności
     * @return lista treningów pasujących do typu aktywności
     */
    @GetMapping("/activityType")
    public List<TrainingDto> getAllTrainingsByActivity(@RequestParam ActivityType activityType) {
        return trainingService.findAllTrainingsByActivity(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Aktualizuje istniejący trening na podstawie ID i danych wejściowych.
     *
     * @param id  identyfikator treningu
     * @param dto dane aktualizacyjne
     * @return zaktualizowany trening jako {@link TrainingDto}
     */
    @PutMapping("/{id}")
    public TrainingDto updateTraining(@PathVariable("id") Long id, @RequestBody TrainingUpdateDto dto) {
        return trainingService.updateTraining(id, dto);
    }

    /**
     * Tworzy nowy trening na podstawie przekazanych danych.
     *
     * @param trainingCreateDto dane treningu do utworzenia
     * @return utworzony trening jako {@link TrainingDto}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto createTraining(@RequestBody TrainingCreateDto trainingCreateDto) {
        User user = userService.findUserByID(trainingCreateDto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Training training = new Training(
                user,
                trainingCreateDto.startTime(),
                trainingCreateDto.endTime(),
                trainingCreateDto.activityType(),
                trainingCreateDto.distance(),
                trainingCreateDto.averageSpeed()
        );

        Training savedTraining = trainingService.saveTraining(training);
        return trainingMapper.toDto(savedTraining);
    }

}
