package ru.my.dreamjob.repository;

import ru.my.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 4. Thymeleaf, Циклы. [#504841 #281377]
 * Интерфейс VacancyRepository (не забываем о принципах SOLID)
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 09.01.2023
 */
public interface VacancyRepository {
    Vacancy save(Vacancy vacancy);

    boolean deleteById(int id);

    boolean update(Vacancy vacancy);

    Optional<Vacancy> findById(int id);

    Collection<Vacancy> findAll();
}
