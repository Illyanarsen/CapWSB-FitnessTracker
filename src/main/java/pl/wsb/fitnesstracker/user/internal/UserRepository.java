package pl.wsb.fitnesstracker.user.internal;

import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Repozytorium użytkowników obsługujące operacje na encji {@link User}.
 * Wykorzystuje {@link JpaRepository} do podstawowych operacji CRUD
 * oraz metody domyślne do dodatkowych operacji filtrujących.
 */
interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    /**
     * Wyszukuje pierwszego użytkownika, którego data urodzenia jest wcześniejsza niż podana.
     *
     * @param date data graniczna
     * @return {@link Optional} z użytkownikiem starszym niż data, lub {@link Optional#empty()}, jeśli nie znaleziono
     */
    default Optional<User> findByTime(LocalDate date) {
        return findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(date))
                .findFirst();
    }

    /**
     * Wyszukuje wszystkich użytkowników, których e-mail zawiera podany fragment (ignorując wielkość liter).
     *
     * @param email fragment e-maila do wyszukania
     * @return lista pasujących użytkowników
     */
    default List<User> findByEmailFragment (String email) {
        String emailLowerCase = email.toLowerCase();
        return findAll().stream()
                .filter(user -> user.getEmail() != null &&
                        user.getEmail().contains(emailLowerCase))
                .toList();
    }


}
