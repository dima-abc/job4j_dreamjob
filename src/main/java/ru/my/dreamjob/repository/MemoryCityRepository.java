package ru.my.dreamjob.repository;

import ru.my.dreamjob.model.City;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 3.2.5. Формы
 * 2. Формы. Списки. [#504854 #284213]
 * MemoryCityRepository хранилище в памяти справочника городов.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 11.01.2023
 */
public class MemoryCityRepository implements CityRepository {
    private final Map<Integer, City> cities = new HashMap<>() {
        {
            put(1, new City(1, "Москва"));
            put(2, new City(2, "Санкт-Петербург"));
            put(3, new City(3, "Екатеренбург"));
        }
    };

    @Override
    public Collection<City> findAll() {
        return cities.values();
    }
}
