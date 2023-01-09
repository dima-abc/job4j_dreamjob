package ru.my.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.my.dreamjob.repository.MemoryVacancyRepository;
import ru.my.dreamjob.repository.VacancyRepository;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 4. Thymeleaf, Циклы. [#504841 #281377]
 * VacancyController контроллер отображения и управления видом вакансий.
 * Работать с вакансиями будем по URI /vacancies/
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 09.01.2023
 */
@Controller
@RequestMapping("/vacancies")
public class VacancyController {
    private final VacancyRepository vacancyRepository = MemoryVacancyRepository.getInstance();

    @GetMapping
    public String getAllVacancy(Model model) {
        model.addAttribute("vacancies", vacancyRepository.findAll());
        return "vacancies/list";
    }
}
