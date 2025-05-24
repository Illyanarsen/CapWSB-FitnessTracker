package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 * Rekord reprezentujący uproszczony widok użytkownika zawierający jedynie identyfikator oraz adres e-mail.
 * <p>
 * Wykorzystywany np. do zwracania wyników wyszukiwania użytkowników po fragmencie adresu e-mail.
 *
 * @param id    identyfikator użytkownika (może być {@code null})
 * @param email adres e-mail użytkownika
 */
public record UserSimpleEmail(@Nullable Long id, String email) {
}
