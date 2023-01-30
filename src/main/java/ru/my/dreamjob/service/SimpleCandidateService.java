package ru.my.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.my.dreamjob.model.Candidate;
import ru.my.dreamjob.model.dto.FileDto;
import ru.my.dreamjob.repository.CandidateRepository;

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
@Service
public class SimpleCandidateService implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final FileService fileService;

    public SimpleCandidateService(CandidateRepository candidateRepository, FileService fileService) {
        this.candidateRepository = candidateRepository;
        this.fileService = fileService;
    }

    private void saveNewFile(Candidate candidate, FileDto image) {
        var file = fileService.save(image);
        candidate.setFileId(file.getId());
    }

    @Override
    public Candidate save(Candidate candidate, FileDto image) {
        saveNewFile(candidate, image);
        return candidateRepository.save(candidate);
    }

    @Override
    public boolean deleteById(int id) {
        var candidateOptional = findById(id);
        if (candidateOptional.isEmpty()) {
            return false;
        }
        var isDeleted = candidateRepository.deleteById(id);
        fileService.deleteById(candidateOptional.get().getFileId());
        return isDeleted;
    }

    @Override
    public boolean update(Candidate candidate, FileDto image) {
        var isNewFileEmpty = image.getContent().length == 0;
        if (isNewFileEmpty) {
            return candidateRepository.update(candidate);
        }
        var oldFile = candidate.getFileId();
        saveNewFile(candidate, image);
        var isUpdate = candidateRepository.update(candidate);
        fileService.deleteById(oldFile);
        return isUpdate;
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
