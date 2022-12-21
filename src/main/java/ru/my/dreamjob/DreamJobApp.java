package ru.my.dreamjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.1.1. Hello world для Web
 * 1. Что такое клиент - серверное приложение. [#504837 #280477]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 21.12.2022
 */
@SpringBootApplication
public class DreamJobApp {
    private static final Logger LOG = LoggerFactory.getLogger(DreamJobApp.class.getSimpleName());
    private static final String START_PAGE = "http://localhost:8080/index";
    public static void main(String[] args) {
        SpringApplication.run(DreamJobApp.class, args);
        LOG.info("Go to: {}", START_PAGE);
    }
}
