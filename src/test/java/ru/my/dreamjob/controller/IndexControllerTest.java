package ru.my.dreamjob.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.8. Spring Test, Mockito
 * 2. Тестируем VacancyController [#504867 #288430]
 * Test IndexController.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 05.02.2023
 */
public class IndexControllerTest {
    @Test
    public void whenRequestGetIndexThenGetIndexPage() {
        IndexController indexController = new IndexController();
        var view = indexController.getIndex();
        assertThat(view).isEqualTo("index");
    }
}