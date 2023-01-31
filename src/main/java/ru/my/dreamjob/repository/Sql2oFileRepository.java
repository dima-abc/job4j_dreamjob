package ru.my.dreamjob.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.my.dreamjob.model.File;

import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.6. Database в Web
 * 1. Подключение к базе в веб приложении. Хранение вакансий. [#504859 #284849]
 * Sql2oFileRepository хранилище путей к файлам в базе данных
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 31.01.2023
 */
@Repository
public class Sql2oFileRepository implements FileRepository {
    private final Sql2o sql2o;

    public Sql2oFileRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public File save(File file) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                            "INSERT  INTO files(name, path) VALUES (:name, :path)", true)
                    .addParameter("name", file.getName())
                    .addParameter("path", file.getPath());
            int generateId = query.executeUpdate().getKey(Integer.class);
            file.setId(generateId);
        }
        return file;
    }

    @Override
    public Optional<File> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                            "SELECT * FROM files WHERE  id = :id")
                    .addParameter("id", id);
            var file = query.executeAndFetchFirst(File.class);
            return Optional.ofNullable(file);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                            "DELETE FROM files WHERE id = :id")
                    .addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }
}
