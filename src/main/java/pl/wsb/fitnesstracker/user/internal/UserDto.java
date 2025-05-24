package pl.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Reprezentacja DTO (Data Transfer Object) użytkownika w systemie FitnessTracker.
 * Służy do przesyłania pełnych danych użytkownika między warstwami aplikacji,
 * np. do tworzenia, edycji i pobierania szczegółów użytkownika.
 *
 * @param id        unikalny identyfikator użytkownika (może być {@code null} przy tworzeniu nowego)
 * @param firstName imię użytkownika
 * @param lastName  nazwisko użytkownika
 * @param birthdate data urodzenia użytkownika w formacie "yyyy-MM-dd"
 * @param email     adres e-mail użytkownika
 */
record UserDto(@Nullable Long id, String firstName, String lastName,
               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
               String email) {

}
