package ru.my.dreamjob.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.my.dreamjob.model.City;

import java.util.Collection;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.6. Database в Web
 * 1. Подключение к базе в веб приложении. Хранение вакансий. [#504859 #284849]
 * Sql2oCityRepository управление хранилищем в базе данных модели Citi
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 31.01.2023
 */
@Repository
public class Sql2oCityRepository implements CityRepository {

    private final Sql2o sql2o;

    public Sql2oCityRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<City> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM cities");
            return query.executeAndFetch(City.class);
        }
    }
}
