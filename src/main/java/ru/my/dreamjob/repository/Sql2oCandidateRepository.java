package ru.my.dreamjob.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.my.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.6. Database в Web
 * 1. Подключение к базе в веб приложении. Хранение вакансий. [#504859 #284849]
 * Sql2oCandidateRepository управление хранилищем в базе данных модели Vacancy.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 31.01.2023
 */
@Repository
public class Sql2oCandidateRepository implements CandidateRepository {
    private final Sql2o sql2o;

    public Sql2oCandidateRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
        this.sql2o.setDefaultColumnMappings(Candidate.COLUMN_MAPPING);
    }

    @Override
    public Candidate save(Candidate candidate) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO candidates(name, description, creation_date, city_id, file_id) 
                    VALUES (:name, :description, :createDate, :cityId, :fileId)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("name", candidate.getName())
                    .addParameter("description", candidate.getDescription())
                    .addParameter("createDate", candidate.getCreateDate())
                    .addParameter("cityId", candidate.getCityId())
                    .addParameter("fileId", candidate.getFileId());
            int generateKey = query.executeUpdate().getKey(Integer.class);
            candidate.setId(generateKey);
            return candidate;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM candidates WHERE id = :id")
                    .addParameter("id", id);
            int affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean update(Candidate candidate) {
        try (var connection = sql2o.open()) {
            var sql = """
                    UPDATE candidates SET name = :name, description = :description, 
                    creation_date = :createDate, city_id = :cityId, file_id = :fileId
                    WHERE id = :id        
                    """;
            var query = connection.createQuery(sql)
                    .addParameter("name", candidate.getName())
                    .addParameter("description", candidate.getDescription())
                    .addParameter("createDate", candidate.getCreateDate())
                    .addParameter("cityId", candidate.getCityId())
                    .addParameter("fileId", candidate.getFileId())
                    .addParameter("id", candidate.getId());
            int affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Optional<Candidate> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM candidates WHERE id = :id")
                    .addParameter("id", id);
            var candidate = query.executeAndFetchFirst(Candidate.class);
            return Optional.ofNullable(candidate);
        }
    }

    @Override
    public Collection<Candidate> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM candidates");
            return query.executeAndFetch(Candidate.class);
        }
    }
}
