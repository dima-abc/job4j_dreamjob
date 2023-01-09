package ru.my.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.my.dreamjob.repository.MemoryCandidateRepository;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 5. Список кандидатов [#504842 #281404]
 * CandidateController контроллер отображения и управления видом кандидатами.
 * Работать с кандидатами будем по URI /candidates
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 09.01.2023
 */
@Controller
@RequestMapping("/candidates")
public class CandidateController {
    private final MemoryCandidateRepository candidates = MemoryCandidateRepository.getInstance();

    @GetMapping("/")
    public String getAllCandidate(Model model) {
        model.addAttribute("candidates", candidates.findAll());
        return "candidates/list";
    }
}
