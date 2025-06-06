package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;

/**
 * Obiekt transferowy (DTO) służący do tworzenia nowego treningu.
 * Zawiera podstawowe dane potrzebne do zapisania treningu w systemie.
 *
 * Uwaga: Nie zawiera pola `id`, ponieważ identyfikator jest generowany automatycznie przez system.
 *
 * @param userId        ID użytkownika przypisanego do treningu
 * @param startTime     Czas rozpoczęcia treningu
 * @param endTime       Czas zakończenia treningu
 * @param activityType  Typ aktywności (np. BIEGANIE, TENIS)
 * @param distance      Przebyty dystans w kilometrach
 * @param averageSpeed  Średnia prędkość w km/h
 */
public record TrainingCreateDto(
        Long userId,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        double distance,
        double averageSpeed
) {}
