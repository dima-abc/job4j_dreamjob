package ru.my.dreamjob.service;

import ru.my.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 3.2.4. Архитектура Web приложений
 * 1. Слоеная архитектура. [#504851 #283148]
 * VacancyService интерфейс описывающий поведение слоя сервиса вакансий.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 10.01.2023
 */
public interface VacancyService {
    Vacancy save(Vacancy vacancy);

    boolean deleteById(int id);

    boolean update(Vacancy vacancy);

    Optional<Vacancy> findById(int id);

    Collection<Vacancy> findAll();
}
