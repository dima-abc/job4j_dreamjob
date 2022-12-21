package ru.my.dreamjob.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.1.1. Hello world для Web
 * 1. Что такое клиент - серверное приложение. [#504837 #280477]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 21.12.2022
 */
@RestController
public class IndexControl {
    @GetMapping("/index")
    public String index() {
        return "Greetings from spring Boot!";
    }
}
