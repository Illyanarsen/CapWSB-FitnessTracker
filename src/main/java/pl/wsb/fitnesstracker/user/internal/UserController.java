package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleEmail;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Kontroler REST dla operacji CRUD na użytkownikach systemu FitnessTracker.
 * Udostępnia endpointy do tworzenia, odczytywania, aktualizowania i usuwania użytkowników,
 * a także do ich wyszukiwania według różnych kryteriów.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    /**
     * Zwraca pełną listę wszystkich użytkowników w systemie.
     *
     * @return lista użytkowników jako {@link UserDto}
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Zwraca uproszczoną listę wszystkich użytkowników (imię i nazwisko).
     *
     * @return lista użytkowników jako {@link UserSimpleDto}
     */
    @GetMapping("/simple")
    public List<UserSimpleDto> getAllSimpleUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimple)
                .toList();
    }

    /**
     * Pobiera szczegóły użytkownika na podstawie ID.
     *
     * @param id identyfikator użytkownika
     * @return użytkownik jako {@link Optional} z {@link UserDto}, jeśli istnieje
     */
    @GetMapping("/{id}")
    public Optional<UserDto> getDetailsAboutUserById(@PathVariable("id") Long id) {
        return userService.findUserByID(id).map(userMapper::toDto);

    }

    /**
     * Pobiera szczegóły użytkownika na podstawie dokładnego adresu e-mail.
     *
     * @param email adres e-mail użytkownika
     * @return użytkownik jako {@link Optional} z {@link UserDto}, jeśli istnieje
     */
    @GetMapping("/email")
    public Optional<UserDto> getDetailsAboutUserByEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email)
                .map(userMapper::toDto);
    }

    /**
     * Tworzy nowego użytkownika.
     *
     * @param userDto dane nowego użytkownika
     * @return utworzony użytkownik (obecnie zwraca {@code null})
     * @throws InterruptedException w przypadku problemów z zapisem
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // TODO: Implement the method to add a new user.
        //  You can use the @RequestBody annotation to map the request body to the UserDto object.
        User addedUser = userMapper.toEntity(userDto);
        userService.createUser(addedUser);

        return null;
    }

    /**
     * Usuwa użytkownika o podanym ID.
     *
     * @param id identyfikator użytkownika do usunięcia
     * @throws InterruptedException w przypadku błędów wykonania
     * @throws ResponseStatusException jeśli użytkownik nie istnieje
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id) throws InterruptedException {
        User user = userService.findUserByID(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userService.deleteUser(user);
    }

    /**
     * Zwraca listę użytkowników starszych niż podana data.
     *
     * @param date data graniczna
     * @return lista użytkowników jako {@link UserDto}
     */
    @GetMapping("/older/{time}")
    public List<UserDto> getAllUsersOlderThan(@PathVariable("time") LocalDate date) {
        return userService.findUserOlderThan(date)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Aktualizuje dane użytkownika na podstawie podanego ID.
     *
     * @param id      identyfikator użytkownika
     * @param userDto nowe dane użytkownika
     * @throws ResponseStatusException jeśli użytkownik nie istnieje
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        User existingUser = userService.findUserByID(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User newUser=userMapper.toEntity(userDto);

        User updatedUser = existingUser.update(
                newUser.getFirstName(),
                newUser.getLastName(),
                newUser.getBirthdate(),
                newUser.getEmail()
        );

        userService.updateUser(updatedUser);
    }

    /**
     * Zwraca listę użytkowników, których adres e-mail zawiera podany fragment (ignoruje wielkość liter).
     *
     * @param email fragment adresu e-mail do wyszukania
     * @return lista pasujących użytkowników jako {@link UserSimpleEmail}
     */
    @GetMapping("/email/fragment")
    public List<UserSimpleEmail> getDetailsAboutUserByEmailFragment(@RequestParam("email") String email) {
        return userService.getUserByEmailFragment(email)
                .stream()
                .map(userMapper::toSimpleEmail)
                .toList();
    }
}