package ru.my.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.my.dreamjob.model.Candidate;
import ru.my.dreamjob.service.CandidateService;
import ru.my.dreamjob.service.SimpleCandidateService;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 5. Список кандидатов [#504842 #281404]
 * CandidateController контроллер отображения и управления видом кандидатами.
 * Работать с кандидатами будем по URI /candidates
 * 3.2.4. Архитектура Web приложений
 * 1. Слоеная архитектура. [#504851 #283148]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 09.01.2023
 */
@Controller
@RequestMapping("/candidates")
public class CandidateController {
    private final CandidateService candidateService = SimpleCandidateService.getInstance();

    @GetMapping
    public String getAllCandidate(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates/list";
    }

    @GetMapping("create")
    public String getCreationPageCandidate() {
        return "candidates/create";
    }

    @PostMapping("/create")
    public String createCandidate(@ModelAttribute Candidate candidate) {
        candidateService.save(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/{id}")
    public String getByIdCandidate(Model model, @PathVariable int id) {
        var candidateOptional = candidateService.findById(id);
        if (candidateOptional.isEmpty()) {
            model.addAttribute("message", "Кандидат с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("candidate", candidateOptional.get());
        return "candidates/one";
    }

    @PostMapping("/update")
    public String updateCandidate(@ModelAttribute Candidate candidate, Model model) {
        var isUpdate = candidateService.update(candidate);
        if (!isUpdate) {
            model.addAttribute("message", "Кандидат с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/candidates";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = candidateService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Кандидат с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/candidates";
    }
}
