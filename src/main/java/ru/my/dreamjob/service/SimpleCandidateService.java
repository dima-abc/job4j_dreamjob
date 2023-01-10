package ru.my.dreamjob.service;

import ru.my.dreamjob.model.Candidate;
import ru.my.dreamjob.repository.CandidateRepository;
import ru.my.dreamjob.repository.MemoryCandidateRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 3.2.4. Архитектура Web приложений
 * 1. Слоеная архитектура. [#504851 #283148]
 * SimpleCandidateService реализация слоя сервиса кандидатов.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 10.01.2023
 */
public class SimpleCandidateService implements CandidateService {
    private static final SimpleCandidateService INSTANCE = new SimpleCandidateService();
    private final CandidateRepository candidateRepository = MemoryCandidateRepository.getInstance();

    public static SimpleCandidateService getInstance() {
        return INSTANCE;
    }

    @Override
    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public boolean deleteById(int id) {
        return candidateRepository.deleteById(id);
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidateRepository.update(candidate);
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return candidateRepository.findById(id);
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidateRepository.findAll();
    }
}
