package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Encja reprezentująca użytkownika systemu FitnessTracker.
 * Przechowuje podstawowe dane osobowe i identyfikacyjne.
 */
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Konstruktor tworzący nowego użytkownika (do zapisu w bazie).
     * Nie powinien zawierać ID – ID generuje się automatycznie.
     *
     * @param firstName imię
     * @param lastName nazwisko
     * @param birthdate data urodzenia
     * @param email adres e-mail
     */
    public User(
            final String firstName,
            final String lastName,
            final LocalDate birthdate,
            final String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;

        }

    /**
     * Konstruktor pomocniczy wykorzystywany m.in. przy aktualizacji danych,
     * kiedy zachodzi potrzeba ustawienia ID użytkownika.
     *
     * @param firstName imię
     * @param lastName nazwisko
     * @param birthdate data urodzenia
     * @param email adres e-mail
     * @param id identyfikator użytkownika
     */
    public User(String firstName, String lastName, LocalDate birthdate, String email, Long id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.id = id;
    }

    /**
     * Tworzy nową instancję użytkownika zaktualizowaną o nowe dane,
     * zachowując to samo ID. Stosowane przy aktualizacji danych.
     *
     * @param firstName nowe imię
     * @param lastName nowe nazwisko
     * @param birthdate nowa data urodzenia
     * @param email nowy adres e-mail
     * @return nowy obiekt {@link User} z aktualnymi danymi
     */
    public User update(String firstName, String lastName, LocalDate birthdate, String email) {
        return new User(firstName, lastName, birthdate, email, this.getId());
    }
}

