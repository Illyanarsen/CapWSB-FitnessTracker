package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementacja usług biznesowych dla zarządzania użytkownikami.
 * Obsługuje operacje tworzenia, aktualizacji, usuwania oraz wyszukiwania użytkowników.
 * Klasa realizuje interfejsy {@link UserService} oraz {@link UserProvider}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    /**
     * Tworzy nowego użytkownika w systemie.
     *
     * @param user encja użytkownika do utworzenia
     * @return utworzony użytkownik
     * @throws IllegalArgumentException jeśli użytkownik posiada już ID (czyli istnieje w bazie)
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Usuwa użytkownika z systemu.
     *
     * @param user użytkownik do usunięcia
     * @throws IllegalArgumentException jeśli użytkownik nie ma ID
     */
    public void deleteUser(final User user) {
        log.info("Deleting User {}", user);
        if (user.getId() == null) {
            throw new IllegalArgumentException("User not found, deletion is not permitted!");
        }
        userRepository.delete(user);
    }

    /**
     * Pobiera użytkownika na podstawie identyfikatora.
     *
     * @param userId ID użytkownika
     * @return {@link Optional} z użytkownikiem lub {@link Optional#empty()}
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Pobiera użytkownika na podstawie adresu e-mail.
     *
     * @param email adres e-mail użytkownika
     * @return {@link Optional} z użytkownikiem lub {@link Optional#empty()}
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Zwraca listę wszystkich użytkowników w systemie.
     *
     * @return lista użytkowników
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Pobiera użytkownika na podstawie identyfikatora.
     * Alias dla {@link #getUser(Long)}.
     *
     * @param id ID użytkownika
     * @return {@link Optional} z użytkownikiem lub {@link Optional#empty()}
     */
    public Optional<User> findUserByID(Long id){
        return userRepository.findById(id);
    }

    /**
     * Wyszukuje użytkownika starszego niż podana data.
     *
     * @param date data graniczna
     * @return {@link Optional} z użytkownikiem lub {@link Optional#empty()}
     */
    public Optional<User> findUserOlderThan(LocalDate date){ return userRepository.findByTime(date); }

    /**
     * Aktualizuje dane użytkownika w systemie.
     *
     * @param updatedUser zmodyfikowany użytkownik do zapisania
     */
    public void updateUser(User updatedUser) { userRepository.save(updatedUser); }


    /**
     * Wyszukuje użytkowników, których adres e-mail zawiera podany fragment (ignorując wielkość liter).
     *
     * @param email fragment adresu e-mail
     * @return lista pasujących użytkowników
     */
    public List<User> getUserByEmailFragment(final String email) {
        return userRepository.findByEmailFragment(email);
    }

}