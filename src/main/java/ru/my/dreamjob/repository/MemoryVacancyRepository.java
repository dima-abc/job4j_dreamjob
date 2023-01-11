package ru.my.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.my.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 4. Thymeleaf, Циклы. [#504841 #281377]
 * MemoryVacancyRepository хранилище данных вакансий в памяти.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 09.01.2023
 */
@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    public MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Description intern JOB Java"));
        save(new Vacancy(0, "Junior Java Developer", "Description Junior JOB Java Developer"));
        save(new Vacancy(0, "Junior+ Java Developer", "Description Junior+ JOB Java Developer"));
        save(new Vacancy(0, "Middle Java Developer", "Description Middle JOB Java Developer"));
        save(new Vacancy(0, "Middle+ Java Developer", "Description Middle+ JOB Java Developer"));
        save(new Vacancy(0, "Senior Java Developer", "Description Senior JOB Java Developer"));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(
                vacancy.getId(),
                (id, oldVacancy) -> new Vacancy(
                        oldVacancy.getId(),
                        vacancy.getTitle(),
                        vacancy.getDescription())
        ) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}
