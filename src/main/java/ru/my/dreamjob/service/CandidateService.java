package ru.my.dreamjob.service;

import ru.my.dreamjob.model.Candidate;
import ru.my.dreamjob.model.dto.FileDto;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 3.2.4. Архитектура Web приложений
 * 1. Слоеная архитектура. [#504851 #283148]
 * CandidateService интерфейс описывающий поведение слоя сервиса кандидатов.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 10.01.2023
 */
public interface CandidateService {
    Candidate save(Candidate candidate, FileDto image);

    boolean deleteById(int id);

    boolean update(Candidate candidate, FileDto image);

    Optional<Candidate> findById(int id);

    Collection<Candidate> findAll();
}
