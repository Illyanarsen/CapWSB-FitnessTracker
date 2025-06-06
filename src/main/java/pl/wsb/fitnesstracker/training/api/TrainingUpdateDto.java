package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;

/**
 * Obiekt transferowy (DTO) służący do aktualizacji treningu.
 * <p>
 * Przekazuje dane potrzebne do zmodyfikowania istniejącego treningu, takie jak:
 * identyfikator treningu, identyfikator użytkownika, przedziały czasowe,
 * rodzaj aktywności oraz opcjonalnie dystans i średnia prędkość.
 * </p>
 *
 * Pola {@code distance} i {@code averageSpeed} są typu {@link Double},
 * co pozwala na rozróżnienie między brakiem aktualizacji a aktualizacją do zera.
 *
 * @param id            identyfikator treningu, który ma być zaktualizowany
 * @param userId        identyfikator użytkownika powiązanego z treningiem
 * @param startTime     czas rozpoczęcia treningu
 * @param endTime       czas zakończenia treningu
 * @param activityType  typ aktywności fizycznej (np. bieganie, pływanie)
 * @param distance      przebyty dystans w kilometrach (opcjonalny)
 * @param averageSpeed  średnia prędkość w km/h (opcjonalna)
 */
public record TrainingUpdateDto(
        Long id,
        Long userId,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        Double distance,
        Double averageSpeed
) {}
