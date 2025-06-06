package pl.wsb.fitnesstracker.training.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.training.api.Training;

import java.util.Date;
import java.util.List;

/**
 * Interfejs repozytorium JPA do zarządzania encjami {@link Training}.
 * Umożliwia wykonywanie operacji CRUD oraz wyszukiwanie treningów po określonych kryteriach.
 * Zawiera domyślne metody filtrujące treningi w pamięci (po pobraniu wszystkich rekordów).
 */
interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Zwraca listę treningów, których czas zakończenia jest po podanej dacie.
     *
     * @param date data graniczna
     * @return lista treningów zakończonych po dacie {@code date}
     */
    default List<Training> findByTime(Date date) {
        return findAll().stream()
                .filter(training -> training.getEndTime().after(date))
                .toList();
    }

    /**
     * Zwraca listę treningów przypisanych do użytkownika o wskazanym identyfikatorze.
     *
     * @param id identyfikator użytkownika
     * @return lista treningów użytkownika o ID {@code id}
     */
    default List<Training> findByUserId(Long id) {
        return findAll().stream()
                .filter(training -> training.getUser().getId().equals(id))
                .toList();
    }

    /**
     * Zwraca listę treningów o określonym typie aktywności.
     *
     * @param activityType typ aktywności (np. RUNNING, SWIMMING)
     * @return lista treningów o danym typie aktywności
     */
    default List<Training> findByActivity(ActivityType activityType) {
        return findAll().stream()
                .filter(training -> training.getActivityType().equals(activityType))
                .toList();
    }
}
