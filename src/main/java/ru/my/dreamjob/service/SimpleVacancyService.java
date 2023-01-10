package ru.my.dreamjob.service;

import ru.my.dreamjob.model.Vacancy;
import ru.my.dreamjob.repository.MemoryVacancyRepository;
import ru.my.dreamjob.repository.VacancyRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 3.2.4. Архитектура Web приложений
 * 1. Слоеная архитектура. [#504851 #283148]
 * SimpleVacancyService реализация слоя сервиса вакансий.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 10.01.2023
 */
public class SimpleVacancyService implements VacancyService {
    private static final SimpleVacancyService INSTANCE = new SimpleVacancyService();

    private final VacancyRepository vacancyRepository = MemoryVacancyRepository.getInstance();

    public static SimpleVacancyService getInstance() {
        return INSTANCE;
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    @Override
    public boolean deleteById(int id) {
        return vacancyRepository.deleteById(id);
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancyRepository.update(vacancy);
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return vacancyRepository.findById(id);
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }
}
