package ru.my.dreamjob.repository;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.my.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
@ThreadSafe
public class MemoryCandidateRepository implements CandidateRepository {
    private final AtomicInteger nextId = new AtomicInteger(0);
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    public MemoryCandidateRepository() {
        save(new Candidate(0, "Ivan I.I.", "Intern JAVA Developer"));
        save(new Candidate(0, "Sidorov V.I.", "Junior Java Developer"));
        save(new Candidate(0, "Kholodkov R.S.", "Junior+ Java Developer"));
        save(new Candidate(0, "Mishin V.V.", "Middle Java Developer"));
        save(new Candidate(0, "Karpun S.V.", "Middle+ Java Developer"));
        save(new Candidate(0, "Farsh D.V.", "Senior Java Developer"));
    }

    @Override
    public Candidate save(Candidate candidate) {
        return candidates.computeIfAbsent(nextId.incrementAndGet(),
                key -> new Candidate(key, candidate.getName(), candidate.getDescription()));
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
