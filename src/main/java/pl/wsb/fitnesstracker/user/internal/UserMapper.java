package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleEmail;

/**
 * Mapper konwertujący obiekty encji {@link User} na obiekty DTO i odwrotnie.
 * Umożliwia tworzenie uproszczonych i pełnych reprezentacji użytkownika,
 * wykorzystywanych do komunikacji między warstwami systemu.
 */
@Component
class UserMapper {

    /**
     * Konwertuje encję {@link User} na pełny obiekt {@link UserDto}.
     *
     * @param user encja użytkownika
     * @return DTO zawierające pełne dane użytkownika
     */
    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    /**
     * Konwertuje DTO {@link UserDto} na encję {@link User}.
     *
     * @param userDto DTO z danymi użytkownika
     * @return encja użytkownika
     */
    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

    /**
     * Tworzy uproszczony DTO {@link UserSimpleDto} zawierający ID, imię i nazwisko użytkownika.
     *
     * @param user encja użytkownika
     * @return uproszczony DTO użytkownika
     */
    UserSimpleDto toSimple(User user){
        return new UserSimpleDto(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

    /**
     * Tworzy uproszczony DTO {@link UserSimpleEmail} zawierający ID i e-mail użytkownika.
     *
     * @param user encja użytkownika
     * @return uproszczony DTO zawierający tylko ID i e-mail
     */
    UserSimpleEmail toSimpleEmail(User user){
        return new UserSimpleEmail(user.getId(), user.getEmail());
    }


}
