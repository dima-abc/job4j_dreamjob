package ru.my.dreamjob.repository;

import ru.my.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 5. Список кандидатов [#504842 #281404]
 * Интерфейс CandidateRepository (не забываем о принципах SOLID)
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 09.01.2023
 */
public interface CandidateRepository {
    Candidate save(Candidate candidate);

    boolean deleteById(int id);

    boolean update(Candidate candidate);

    Optional<Candidate> findById(int id);

    Collection<Candidate> findAll();
}
