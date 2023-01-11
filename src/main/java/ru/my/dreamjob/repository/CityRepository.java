package ru.my.dreamjob.repository;

import ru.my.dreamjob.model.City;

import java.util.Collection;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 3.2.5. Формы
 * 2. Формы. Списки. [#504854 #284213]
 * Интерфейс CityRepository описывает поведение хранилища городов.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 11.01.2023
 */
public interface CityRepository {
    Collection<City> findAll();
}
