package ru.my.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.my.dreamjob.model.Candidate;
import ru.my.dreamjob.model.User;
import ru.my.dreamjob.model.dto.FileDto;
import ru.my.dreamjob.service.CandidateService;
import ru.my.dreamjob.service.CityService;

import javax.servlet.http.HttpSession;

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
    private final CandidateService candidateService;
    private final CityService cityService;

    public CandidateController(CandidateService candidateService,
                               CityService cityService) {
        this.candidateService = candidateService;
        this.cityService = cityService;
    }

    @GetMapping
    public String getAllCandidate(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates/list";
    }

    @GetMapping("create")
    public String getCreationPageCandidate(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("cities", cityService.findAll());
        return "candidates/create";
    }

    @PostMapping("/create")
    public String createCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam MultipartFile file,
                                  Model model) {
        try {
            candidateService.save(candidate,
                    new FileDto(file.getOriginalFilename(), file.getBytes()));
        return "redirect:/candidates";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getByIdCandidate(Model model, @PathVariable int id,
                                   HttpSession session) {
        var candidateOptional = candidateService.findById(id);
        if (candidateOptional.isEmpty()) {
            model.addAttribute("message", "Кандидат с указанным идентификатором не найдена");
            return "errors/404";
        }
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("cities", cityService.findAll());
        model.addAttribute("candidate", candidateOptional.get());
        return "candidates/one";
    }

    @PostMapping("/update")
    public String updateCandidate(@ModelAttribute Candidate candidate,
                                  MultipartFile file,
                                  Model model) {
        try {
            var isUpdate = candidateService.update(candidate,
                    new FileDto(file.getOriginalFilename(), file.getBytes()));
            if (!isUpdate) {
                model.addAttribute("message", "Кандидат с указанным идентификатором не найдена");
                return "errors/404";
            }
            return "redirect:/candidates";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
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
