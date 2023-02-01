package ru.my.dreamjob.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.my.dreamjob.configuration.DatasourceConfiguration;
import ru.my.dreamjob.model.File;
import ru.my.dreamjob.model.Vacancy;

import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.6. Database в Web
 * 3. Тестирование репозиториев [#504862 #285623]
 * TEST Sql2oVacancyRepository
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 01.02.2023
 */
class Sql2oVacancyRepositoryTest {
    private static Sql2oVacancyRepository sql2oVacancyRepository;

    private static Sql2oFileRepository sql2oFileRepository;

    private static File file;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oVacancyRepositoryTest
                .class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oVacancyRepository = new Sql2oVacancyRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);

        file = new File("test", "test");
        sql2oFileRepository.save(file);
    }

    @AfterAll
    public static void deleteFile() {
        sql2oFileRepository.deleteById(file.getId());
    }

    @AfterEach
    public void clearVacancies() {
        var vacancies = sql2oVacancyRepository.findAll();
        for (var vacancy : vacancies) {
            sql2oVacancyRepository.deleteById(vacancy.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var vacancy = sql2oVacancyRepository.save(new Vacancy(0, "title",
                "description", true,
                1, file.getId()));
        var savedVacancy = sql2oVacancyRepository.findById(vacancy.getId()).get();
        assertThat(savedVacancy)
                .usingRecursiveComparison()
                .isEqualTo(vacancy);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var vacancy1 = sql2oVacancyRepository.save(new Vacancy(0, "title1",
                "description1", true,
                1, file.getId()));
        var vacancy2 = sql2oVacancyRepository.save(new Vacancy(0, "title2",
                "description2", true,
                2, file.getId()));
        var vacancy3 = sql2oVacancyRepository.save(new Vacancy(0, "title3",
                "description3", true,
                3, file.getId()));
        var result = sql2oVacancyRepository.findAll();
        var expected = List.of(vacancy1, vacancy2, vacancy3);
        assertThat(result)
                .isEqualTo(expected);
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oVacancyRepository.findAll())
                .isEqualTo(emptyList());
        assertThat(sql2oVacancyRepository.findById(0))
                .isEqualTo(empty());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var vacancy = sql2oVacancyRepository.save(new Vacancy(0, "title", "description",
                true, 2, file.getId()));
        var isDeleted = sql2oVacancyRepository.deleteById(vacancy.getId());
        var savedVacancy = sql2oVacancyRepository.findById(vacancy.getId());
        assertThat(isDeleted).isTrue();
        assertThat(savedVacancy).isEqualTo(empty());
    }

    @Test
    public void whenDeletedByInvalidIdThenGetFalse() {
        assertThat(sql2oVacancyRepository.deleteById(0))
                .isFalse();
    }

    @Test
    public void whenUpdateThenGetUpdate() {
        var vacancy = sql2oVacancyRepository.save(
                new Vacancy(0, "title", "description",
                        true, 2, file.getId()));
        var updateVacancy = new Vacancy(
                vacancy.getId(), "new title", "new description",
                !vacancy.getVisible(), 1, file.getId());
        var isUpdate = sql2oVacancyRepository.update(updateVacancy);
        var savedVacancy = sql2oVacancyRepository.findById(updateVacancy.getId()).get();
        assertThat(isUpdate).isTrue();
        assertThat(savedVacancy)
                .usingRecursiveComparison()
                .isEqualTo(updateVacancy);
    }

    @Test
    public void whenUpdateUnExistingThenGetFalse() {
        var vacancy = new Vacancy(0, "title", "description",
                true, 3, file.getId());
        var isUpdate = sql2oVacancyRepository.update(vacancy);
        assertThat(isUpdate).isFalse();
    }
}