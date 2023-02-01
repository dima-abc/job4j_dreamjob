package ru.my.dreamjob.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.my.dreamjob.configuration.DatasourceConfiguration;
import ru.my.dreamjob.model.Candidate;
import ru.my.dreamjob.model.File;

import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.6. Database в Web
 * 3. Тестирование репозиториев [#504862 #285623]
 * TEST Sql2oсCandidateRepository
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 01.02.2023
 */
class Sql2oCandidateRepositoryTest {
    private static Sql2oCandidateRepository sql2oCandidateRepository;
    private static Sql2oFileRepository sql2oFileRepository;
    private static File file;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oCandidateRepositoryTest
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

        sql2oCandidateRepository = new Sql2oCandidateRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);

        file = new File("file_test", "file/test");
        sql2oFileRepository.save(file);
    }

    @AfterAll
    public static void deleteFile() {
        sql2oFileRepository.deleteById(file.getId());
    }

    @AfterEach
    public void clearCandidates() {
        var candidates = sql2oCandidateRepository.findAll();
        for (var candidate : candidates) {
            sql2oCandidateRepository.deleteById(candidate.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var candidate = sql2oCandidateRepository.save(
                new Candidate(0, "name", "description", 3, file.getId()));
        var savedCandidate = sql2oCandidateRepository.findById(candidate.getId()).get();
        assertThat(savedCandidate)
                .usingRecursiveComparison()
                .isEqualTo(candidate);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var candidate1 = sql2oCandidateRepository.save(
                new Candidate(0, "name1", "description1", 1, file.getId()));
        var candidate2 = sql2oCandidateRepository.save(
                new Candidate(0, "name2", "description2", 2, file.getId()));
        var candidate3 = sql2oCandidateRepository.save(
                new Candidate(0, "name3", "description3", 3, file.getId()));
        var result = sql2oCandidateRepository.findAll();
        var expected = List.of(candidate1, candidate2, candidate3);
        assertThat(result)
                .isEqualTo(expected);
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oCandidateRepository.findAll())
                .isEqualTo(emptyList());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var candidate = sql2oCandidateRepository.save(
                new Candidate(0, "name", "description", 3, file.getId()));
        var isDeleted = sql2oCandidateRepository.deleteById(candidate.getId());
        var savedCandidate = sql2oCandidateRepository.findById(candidate.getId());
        assertThat(isDeleted).isTrue();
        assertThat(savedCandidate).isEqualTo(empty());
    }

    @Test
    public void whenUpdateThenGetUpdate() {
        var candidate = sql2oCandidateRepository.save(
                new Candidate(0, "name", "description", 3, file.getId()));
        var updateCandidate = new Candidate(candidate.getId(), "new Name",
                "new Description", 2, file.getId());
        var isUpdate = sql2oCandidateRepository.update(updateCandidate);
        var saveCandidate = sql2oCandidateRepository.findById(updateCandidate.getId()).get();
        assertThat(isUpdate).isTrue();
        assertThat(saveCandidate)
                .usingRecursiveComparison()
                .isEqualTo(updateCandidate);
    }

    @Test
    public void whenUpdateUnExistingThenGetFalse() {
        var candidate = new Candidate(0, "name", "description", 3, file.getId());
        var isUpdate = sql2oCandidateRepository.update(candidate);
        assertThat(isUpdate).isFalse();
    }
}