package pl.wsb.fitnesstracker.training.api;

import jakarta.annotation.Nullable;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.Date;

/**
 * Obiekt transferowy (DTO) reprezentujący dane pojedynczego treningu.
 * Wykorzystywany do przesyłania pełnych informacji o treningu między warstwami aplikacji
 * (np. kontroler - serwis - frontend).
 *
 * @param id            Unikalny identyfikator treningu (może być null przy tworzeniu nowego)
 * @param user          Użytkownik przypisany do treningu
 * @param startTime     Czas rozpoczęcia treningu
 * @param endTime       Czas zakończenia treningu
 * @param activityType  Typ aktywności wykonywanej w trakcie treningu (np. BIEGANIE, TENIS)
 * @param distance      Przebyty dystans w kilometrach
 * @param averageSpeed  Średnia prędkość w km/h
 */
public record TrainingDto(
        @Nullable Long id,
        User user,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        double distance,
        double averageSpeed
) {
}
