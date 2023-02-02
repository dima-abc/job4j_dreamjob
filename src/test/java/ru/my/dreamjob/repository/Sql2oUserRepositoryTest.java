package ru.my.dreamjob.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.my.dreamjob.configuration.DatasourceConfiguration;
import ru.my.dreamjob.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.6. Database в Web
 * 4. Многопоточность в базе данных [#504860 #285783]
 * TEST Sql2oUserRepository
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 01.02.2023
 */
class Sql2oUserRepositoryTest {
    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class
                .getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }

        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void cleanUsers() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteByEmail(user.getEmail());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var user = new User(0, "mail@mail.ru", "User", "12345678");
        var userOptional = sql2oUserRepository.save(user);
        var saveUser = sql2oUserRepository.findByEmailAndPassword(
                user.getEmail(), user.getPassword());
        assertThat(userOptional)
                .usingRecursiveComparison()
                .isEqualTo(saveUser);
    }

    @Test
    public void whenSaveTwoUserFindUserByEmailAndPasswordThenGetSame() {
        var user1 = new User(0, "mail1@mail.ru", "User1", "02345678");
        var user2 = new User(0, "mail2@mail.ru", "User2", "12345670");
        var saveUser1 = sql2oUserRepository.save(user1);
        var saveUser2 = sql2oUserRepository.save(user2);
        var fundUser2 = sql2oUserRepository.findByEmailAndPassword(user2.getEmail(), user2.getPassword());
        assertThat(saveUser2)
                .usingRecursiveComparison()
                .isEqualTo(fundUser2);
    }

    @Test
    public void whenDontSaveAndFindUserPasswordThenNothingFound() {
        assertThat(sql2oUserRepository.findByEmailAndPassword("", ""))
                .isEqualTo(empty());
    }

    @Test
    public void whenSaveAndFindUserPasswordThenNothingFound() {
        var user1 = new User(0, "mail1@mail.ru", "User1", "02345678");
        var user2 = new User(0, "mail2@mail.ru", "User2", "12345670");
        var saveUser1 = sql2oUserRepository.save(user1);
        var saveUser2 = sql2oUserRepository.save(user2);
        assertThat(sql2oUserRepository.findByEmailAndPassword(user1.getEmail(), user2.getPassword()))
                .isEqualTo(empty());
        assertThat(sql2oUserRepository.findByEmailAndPassword(user2.getEmail(), user1.getPassword()))
                .isEqualTo(empty());
    }

    @Test
    public void whenSaveTwoEqualsUserThenNothingFound() {
        var user = new User(0, "mail@mail.ru", "User", "12345678");
        sql2oUserRepository.save(user);
        assertThat(sql2oUserRepository.save(user))
                .isEqualTo(empty());
    }

    @Test
    public void whenDeleteUserByEmailThenTrue() {
        var user = new User(0, "mail@mail.ru", "User", "12345678");
        sql2oUserRepository.save(user);
        boolean result = sql2oUserRepository.deleteByEmail(user.getEmail());
        assertThat(result).isTrue();
    }

    @Test
    public void whenDeleteUserByEmailThenFalse() {
        var user = new User(0, "mail@mail.ru", "User", "12345678");
        sql2oUserRepository.save(user);
        boolean result = sql2oUserRepository.deleteByEmail("");
        assertThat(result).isFalse();
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var user1 = new User(0, "mail1@mail.ru", "User1", "02345678");
        var user2 = new User(0, "mail2@mail.ru", "User2", "12345670");
        var saveUser1 = sql2oUserRepository.save(user1);
        var saveUser2 = sql2oUserRepository.save(user2);
        var expected = List.of(saveUser1.get(), saveUser2.get());
        var result = sql2oUserRepository.findAll();
        assertThat(result)
                .isEqualTo(expected);
    }
}