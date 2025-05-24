package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 * Rekord reprezentujący uproszczony widok użytkownika systemu FitnessTracker.
 * Zawiera tylko podstawowe dane: identyfikator, imię oraz nazwisko.
 *
 * Wykorzystywany w sytuacjach, gdy pełne dane użytkownika nie są wymagane.
 *
 * @param id        identyfikator użytkownika (może być {@code null})
 * @param firstName imię użytkownika
 * @param lastName  nazwisko użytkownika
 */
public record UserSimpleDto (@Nullable Long id, String firstName, String lastName){}


