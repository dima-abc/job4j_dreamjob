package ru.my.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.my.dreamjob.model.Vacancy;
import ru.my.dreamjob.repository.MemoryVacancyRepository;
import ru.my.dreamjob.repository.VacancyRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @GetMapping("/create")
    public String getCreationPageVacancy() {
        return "vacancies/create";
    }

    @PostMapping("/create")
    public String createVacancy(@ModelAttribute Vacancy vacancy) {
        vacancyRepository.save(vacancy);
        return "redirect:/vacancies";
    }

    @GetMapping("/{id}")
    public String getByIdVacancy(Model model, @PathVariable int id) {
        var vacancyOptional = vacancyRepository.findById(id);
        if (vacancyOptional.isEmpty()) {
            model.addAttribute("message", "Вакансия с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("vacancy", vacancyOptional.get());
        return "vacancies/one";
    }

    @PostMapping("/update")
    public String updateVacancy(@ModelAttribute Vacancy vacancy, Model model) {
        var isUpdate = vacancyRepository.update(vacancy);
        if (!isUpdate) {
            model.addAttribute("message", "Вакансия с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/vacancies";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = vacancyRepository.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Вакансия с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/vacancies";
    }
}
