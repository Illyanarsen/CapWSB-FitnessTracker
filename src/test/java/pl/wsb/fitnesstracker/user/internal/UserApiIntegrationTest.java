package pl.wsb.fitnesstracker.user.internal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.fitnesstracker.IntegrationTest;
import pl.wsb.fitnesstracker.IntegrationTestBase;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@Transactional
@AutoConfigureMockMvc(addFilters = false)
class UserApiIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    public static User generateUser() {
        return new User(randomUUID().toString(), randomUUID().toString(), LocalDate.now(), randomUUID().toString());
    }

    private static User generateUserWithDate(LocalDate date) {
        return new User(randomUUID().toString(), randomUUID().toString(), date, randomUUID().toString());
    }

    @Test
    void shouldReturnAllUsers_whenGettingAllUsers() throws Exception {
        User user1 = existingUser(generateUser());
        User user2 = existingUser(generateUser());

        mockMvc.perform(get("/v1/users").contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].firstName").value(user1.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(user1.getLastName()))
                .andExpect(jsonPath("$[0].birthdate").value(ISO_DATE.format(user1.getBirthdate())))

                .andExpect(jsonPath("$[1].firstName").value(user2.getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(user2.getLastName()))
                .andExpect(jsonPath("$[1].birthdate").value(ISO_DATE.format(user2.getBirthdate())))

                .andExpect(jsonPath("$[2]").doesNotExist());
    }

    @Test
    void shouldReturnAllSimpleUsers_whenGettingAllUsers() throws Exception {
        User user1 = existingUser(generateUser());
        User user2 = existingUser(generateUser());

        mockMvc.perform(get("/v1/users/simple").contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].firstName").value(user1.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(user1.getLastName()))

                .andExpect(jsonPath("$[1].firstName").value(user2.getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(user2.getLastName()))

                .andExpect(jsonPath("$[2]").doesNotExist());
    }

    @Test
    void shouldReturnDetailsAboutUser_whenGettingUserById() throws Exception {
        User user1 = existingUser(generateUser());

        mockMvc.perform(get("/v1/users/{id}", user1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(user1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user1.getLastName()))
                .andExpect(jsonPath("$.birthdate").value(ISO_DATE.format(user1.getBirthdate())))
                .andExpect(jsonPath("$.email").value(user1.getEmail()));

    }

    @Test
    void shouldReturnDetailsAboutUser_whenGettingUserByEmail() throws Exception {
        User user = existingUser(generateUser());

        mockMvc.perform(get("/v1/users/email")
                        .param("email", user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                // Oczekujemy obiektu a nie listy (bo email jest unikalny), więc sprawdzamy pola obiektu, a nie tablicę
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.birthdate").value(user.getBirthdate().toString()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }


    @Test
    void shouldReturnAllUsersOlderThan_whenGettingAllUsersOlderThan() throws Exception {
        User user1 = existingUser(generateUserWithDate(LocalDate.of(2000, 8, 11)));
        existingUser(generateUserWithDate(LocalDate.of(2024, 8, 11)));


        mockMvc.perform(get("/v1/users/older/{time}", LocalDate.of(2024, 8, 10)).contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].firstName").value(user1.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(user1.getLastName()))
                .andExpect(jsonPath("$[0].birthdate").value(ISO_DATE.format(user1.getBirthdate())))

                .andExpect(jsonPath("$[1]").doesNotExist());
    }

    @Test
    void shouldRemoveUserFromRepository_whenDeletingClient() throws Exception {
        User user1 = existingUser(generateUser());


        mockMvc.perform(delete("/v1/users/{userId}", user1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNoContent());

        List<User> allUser = getAllUsers();
        assertThat(allUser).isEmpty();

    }

    @Test
    void shouldPersistUser_whenCreatingUser() throws Exception {

        final String USER_NAME = "Mike";
        final String USER_LAST_NAME = "Scott";
        final String USER_BIRTHDATE = "1999-09-29";
        final String USER_EMAIL = "mike.scott@domain.com";

        String creationRequest = """
                
                {
                "firstName": "%s",
                "lastName": "%s",
                "birthdate": "%s",
                "email": "%s"
                }
                """.formatted(
                USER_NAME,

                USER_LAST_NAME,
                USER_BIRTHDATE,
                USER_EMAIL);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(creationRequest))
                .andDo(log())
                .andExpect(status().isCreated());

        List<User> allUsers = getAllUsers();
        User user = allUsers.get(0);

        assertThat(user.getFirstName()).isEqualTo(USER_NAME);
        assertThat(user.getLastName()).isEqualTo(USER_LAST_NAME);
        assertThat(user.getBirthdate()).isEqualTo(LocalDate.parse(USER_BIRTHDATE));
        assertThat(user.getEmail()).isEqualTo(USER_EMAIL);

    }

    @Test
    void shouldUpdateUser_whenUpdatingUser() throws Exception {
        User user1 = existingUser(generateUser());

        final String USER_NAME = "Mike";
        final String USER_LAST_NAME = "Scott";
        final String USER_BIRTHDATE = "1999-09-29";
        final String USER_EMAIL = "mike.scott@domain.com";

        String updateRequest = """
                
                {
                "firstName": "%s",
                "lastName": "%s",
                "birthdate": "%s",
                "email": "%s"
                }
                """.formatted(
                USER_NAME,

                USER_LAST_NAME,
                USER_BIRTHDATE,
                USER_EMAIL);

        mockMvc.perform(put("/v1/users/{userId}", user1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequest));

        List<User> allUsers = getAllUsers();
        User user = allUsers.get(0);

        assertThat(user.getFirstName()).isEqualTo(USER_NAME);
        assertThat(user.getLastName()).isEqualTo(USER_LAST_NAME);
        assertThat(user.getBirthdate()).isEqualTo(LocalDate.parse(USER_BIRTHDATE));
        assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
    }

    @Test
    void shouldReturnUsersMatchingEmailFragment_whenSearchingByEmail() throws Exception {
        User user1 = existingUser(new User("Alice", "Smith", LocalDate.of(1990, 1, 1), "alice@example.com"));
        User user2 = existingUser(new User("Bob", "Jones", LocalDate.of(1985, 5, 5), "bob123@gmail.com"));
        existingUser(new User("Charlie", "Brown", LocalDate.of(1980, 3, 3), "charlie@yahoo.com"));

        mockMvc.perform(get("/v1/users/email/fragment")
                        .param("email", "gmail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].email").value(user2.getEmail()))
                .andExpect(jsonPath("$[1]").doesNotExist());
    }

    @Test
    void shouldReturnNotFound_whenGettingNonExistingUserById() throws Exception {
        mockMvc.perform(get("/v1/users/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk()) //bo zwracamy optional
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldReturnEmptyList_whenEmailFragmentDoesNotMatchAnyUser() throws Exception {
        existingUser(new User("Alice", "Smith", LocalDate.of(1990, 1, 1), "alice@example.com"));

        mockMvc.perform(get("/v1/users/email/fragment")
                        .param("email", "nonexistingfragment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }


}
