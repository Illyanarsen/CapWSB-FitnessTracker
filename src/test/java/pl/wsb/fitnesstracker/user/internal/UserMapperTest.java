package pl.wsb.fitnesstracker.user.internal;

import org.junit.jupiter.api.Test;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    void shouldMapUserToUserDto() {
        User user = new User("John", "Smith", LocalDate.of(1990, 1, 1), "john@example.com", 5L);

        UserDto dto = mapper.toDto(user);

        assertThat(dto.id()).isEqualTo(5L);
        assertThat(dto.firstName()).isEqualTo("John");
        assertThat(dto.lastName()).isEqualTo("Smith");
        assertThat(dto.birthdate()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(dto.email()).isEqualTo("john@example.com");
    }

    @Test
    void shouldMapUserDtoToUser() {
        UserDto dto = new UserDto(3L, "Anna", "Nowak", LocalDate.of(1985, 5, 20), "anna@domain.com");

        User user = mapper.toEntity(dto);

        assertThat(user.getFirstName()).isEqualTo("Anna");
        assertThat(user.getLastName()).isEqualTo("Nowak");
        assertThat(user.getBirthdate()).isEqualTo(LocalDate.of(1985, 5, 20));
        assertThat(user.getEmail()).isEqualTo("anna@domain.com");
    }
}
