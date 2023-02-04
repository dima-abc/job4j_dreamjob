package ru.my.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 1. Генератор HTML [#504838 #280794]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 21.12.2022
 */
@Controller
public class IndexController {
    @GetMapping({"/", "/index"})
    public String getIndex() {
        return "index";
    }
}
