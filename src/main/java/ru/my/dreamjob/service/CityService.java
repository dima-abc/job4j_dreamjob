package ru.my.dreamjob.service;

import ru.my.dreamjob.model.City;

import java.util.Collection;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 3.2.5. Формы
 * 2. Формы. Списки. [#504854 #284213]
 * CityService интерфейс описывающий поведение слоя сервиса городов.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 11.01.2023
 */
public interface CityService {
    Collection<City> findAll();
}
