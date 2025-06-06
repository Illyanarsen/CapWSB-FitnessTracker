package pl.wsb.fitnesstracker.training.internal;

import jakarta.annotation.Nullable;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.Date;

/**
 * Rekord DTO (Data Transfer Object) reprezentujący dane treningu użytkownika.
 * Używany do komunikacji między warstwą backendową a frontendem lub klientem API.
 *
 * @param id           unikalny identyfikator treningu (może być {@code null} przy tworzeniu)
 * @param user         użytkownik, do którego przypisany jest trening
 * @param startTime    data i godzina rozpoczęcia treningu
 * @param endTime      data i godzina zakończenia treningu
 * @param activityType typ aktywności fizycznej (np. bieganie, pływanie)
 * @param distance     pokonany dystans w kilometrach/metrach (w zależności od przyjętej jednostki)
 * @param averageSpeed średnia prędkość osiągnięta podczas treningu
 */
public record TrainingDto (
        @Nullable Long id,
        User user,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        double distance,
        double averageSpeed
) {
}
