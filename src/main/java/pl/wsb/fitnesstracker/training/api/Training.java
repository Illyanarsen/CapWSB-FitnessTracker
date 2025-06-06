package pl.wsb.fitnesstracker.training.api;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Encja reprezentująca pojedynczy trening użytkownika w systemie FitnessTracker.
 * Zawiera informacje o użytkowniku, czasie rozpoczęcia i zakończenia treningu,
 * rodzaju aktywności, dystansie i średniej prędkości.
 */
@Entity
@Table(name = "trainings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Training {

    /**
     * Unikalny identyfikator treningu (klucz główny).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Użytkownik, do którego przypisany jest trening.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Czas rozpoczęcia treningu.
     */
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    /**
     * Czas zakończenia treningu.
     */
    @Column(name = "end_time", nullable = false)
    private Date endTime;

    /**
     * Typ aktywności fizycznej (np. BIEGANIE, TENIS).
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    /**
     * Przebyty dystans (w kilometrach).
     */
    @Column(name = "distance")
    private double distance;

    /**
     * Średnia prędkość (w km/h).
     */
    @Column(name = "average_speed")
    private double averageSpeed;

    /**
     * Tworzy nowy trening z podanymi parametrami.
     *
     * @param user          użytkownik wykonujący trening
     * @param startTime     czas rozpoczęcia treningu
     * @param endTime       czas zakończenia treningu
     * @param activityType  typ aktywności fizycznej
     * @param distance      przebyty dystans
     * @param averageSpeed  średnia prędkość
     */
    public Training(
            final User user,
            final Date startTime,
            final Date endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }

    /**
     * Aktualizuje bieżący obiekt treningu na podstawie przekazanych danych.
     *
     * @param user          nowy użytkownik
     * @param endTime       nowy czas zakończenia
     * @param startTime     nowy czas rozpoczęcia
     * @param activityType  nowy typ aktywności
     * @param distance      nowy dystans
     * @param averageSpeed  nowa średnia prędkość
     * @return zaktualizowany obiekt treningu
     */
    public Training update(User user, Date endTime, Date startTime,
                           ActivityType activityType, double distance, double averageSpeed) {
        this.user = user;
        this.endTime = endTime;
        this.startTime = startTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
        return this;
    }
}