package ru.my.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.my.dreamjob.model.Candidate;
import ru.my.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 5. Список кандидатов [#504842 #281404]
 * MemoryCandidateRepository хранилище данных кандидатов в памяти
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 09.01.2023
 */
@Repository
public class MemoryCandidateRepository implements CandidateRepository {
    private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();
    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    public MemoryCandidateRepository() {
        save(new Candidate(0, "Ivan I.I.", "Intern JAVA Developer"));
        save(new Candidate(0, "Sidorov V.I.", "Junior Java Developer"));
        save(new Candidate(0, "Kholodkov R.S.", "Junior+ Java Developer"));
        save(new Candidate(0, "Mishin V.V.", "Middle Java Developer"));
        save(new Candidate(0, "Karpun S.V.", "Middle+ Java Developer"));
        save(new Candidate(0, "Farsh D.V.", "Senior Java Developer"));
    }

    public static MemoryCandidateRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(
                candidate.getId(),
                (id, oldCandidate) -> new Candidate(
                        oldCandidate.getId(),
                        candidate.getName(),
                        candidate.getDescription()
                )) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
