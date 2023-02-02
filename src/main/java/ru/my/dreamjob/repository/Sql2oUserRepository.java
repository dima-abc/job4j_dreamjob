package ru.my.dreamjob.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.my.dreamjob.model.User;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2.6. Database в Web
 * 4. Многопоточность в базе данных [#504860 #285783]
 * Sql2oUserRepository реализация хранилища модели User в базе данных(Через Sql2o).
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 01.02.2023
 */
@Repository
public class Sql2oUserRepository implements UserRepository {
    private final Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO users(email, name, password) 
                    VALUES(:email, :name, :password)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("email", user.getEmail())
                    .addParameter("name", user.getName())
                    .addParameter("password", user.getPassword());
            int generateId = query.executeUpdate().getKey(Integer.class);
            user.setId(generateId);
            return Optional.of(user);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = sql2o.open()) {
            var sql = """
                    SELECT * FROM users 
                    WHERE email = :email and password = :password
                    """;
            var query = connection.createQuery(sql)
                    .addParameter("email", email)
                    .addParameter("password", password);
            var user = query.executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

    public boolean deleteByEmail(String email) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM users WHERE email = :email")
                    .addParameter("email", email);
            int affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    public Collection<User> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users");
            return query.executeAndFetch(User.class);
        }
    }
}
