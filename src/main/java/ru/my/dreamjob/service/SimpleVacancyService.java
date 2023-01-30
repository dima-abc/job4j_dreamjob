package ru.my.dreamjob.service;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.my.dreamjob.model.Vacancy;
import ru.my.dreamjob.model.dto.FileDto;
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
@Service
public class SimpleVacancyService implements VacancyService {
    private final VacancyRepository vacancyRepository;

    private final FileService fileService;

    public SimpleVacancyService(VacancyRepository vacancyRepository, FileService fileService) {
        this.vacancyRepository = vacancyRepository;
        this.fileService = fileService;
    }

    private void saveNewFile(Vacancy vacancy, FileDto image) {
        var file = fileService.save(image);
        vacancy.setFileId(file.getId());
    }

    @Override
    public Vacancy save(Vacancy vacancy, FileDto image) {
        saveNewFile(vacancy, image);
        return vacancyRepository.save(vacancy);
    }

    @Override
    public boolean deleteById(int id) {
        var vacancyOptional = findById(id);
        if (vacancyOptional.isEmpty()) {
            return false;
        }
        var isDeleted = vacancyRepository.deleteById(id);
        fileService.deleteById(vacancyOptional.get().getFileId());
        return isDeleted;
    }

    @Override
    public boolean update(Vacancy vacancy, FileDto image) {
        var isNewFileEmpty = image.getContent().length == 0;
        if (isNewFileEmpty) {
            return vacancyRepository.update(vacancy);
        }
        var oldFileId = vacancy.getFileId();
        saveNewFile(vacancy, image);
        var isUpdate = vacancyRepository.update(vacancy);
        fileService.deleteById(oldFileId);
        return isUpdate;
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
