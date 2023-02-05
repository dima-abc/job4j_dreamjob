package ru.my.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;
import ru.my.dreamjob.model.City;
import ru.my.dreamjob.model.Vacancy;
import ru.my.dreamjob.model.dto.FileDto;
import ru.my.dreamjob.service.CityService;
import ru.my.dreamjob.service.VacancyService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.8. Spring Test, Mockito
 * 2. Тестируем VacancyController [#504867 #288430]
 * Test  VacancyController.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 03.02.2023
 */
public class VacancyControllerTest {
    private VacancyService vacancyService;
    private CityService cityService;
    private VacancyController vacancyController;
    private MultipartFile testFile;

    @BeforeEach
    public void initServices() {
        vacancyService = mock(VacancyService.class);
        cityService = mock(CityService.class);
        vacancyController = new VacancyController(vacancyService, cityService);
        testFile = new MockMultipartFile("testFile.img", new byte[]{1, 2, 3});
    }

    @Test
    public void whenRequestVacancyListPageThenGetPageWithVacancies() {
        var vacancy1 = new Vacancy(1, "test1", "desc1", true, 1, 2);
        var vacancy2 = new Vacancy(2, "test2", "desc2", false, 3, 4);
        var expectedVacancies = List.of(vacancy1, vacancy2);
        when(vacancyService.findAll()).thenReturn(expectedVacancies);

        var model = new ConcurrentModel();
        var view = vacancyController.getAllVacancy(model);
        var actualVacancies = model.getAttribute("vacancies");

        assertThat(view).isEqualTo("vacancies/list");
        assertThat(actualVacancies).isEqualTo(expectedVacancies);
    }

    @Test
    public void whenRequestCreationPageVacancyThenGetPageWithCreationVacancyAndAllCities() {
        var city1 = new City(1, "city1");
        var city2 = new City(2, "city2");
        var expectedCities = List.of(city1, city2);
        when(cityService.findAll()).thenReturn(expectedCities);

        var model = new ConcurrentModel();
        var view = vacancyController.getCreationPageVacancy(model);
        var actualCities = model.getAttribute("cities");

        assertThat(view).isEqualTo("vacancies/create");
        assertThat(actualCities).isEqualTo(expectedCities);
    }

    @Test
    public void whenPostVacancyWithFileThenSameDataAndRedirectToVacanciesPage() throws IOException {
        var vacancy = new Vacancy(1, "title", "desc", true, 2, 4);
        var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        var vacancyArgumentCaptor = ArgumentCaptor.forClass(Vacancy.class);
        var fileDtoArgumentCaptor = ArgumentCaptor.forClass(FileDto.class);
        when(vacancyService.save(vacancyArgumentCaptor.capture(), fileDtoArgumentCaptor.capture()))
                .thenReturn(vacancy);

        var model = new ConcurrentModel();
        var view = vacancyController.createVacancy(vacancy, testFile, model);
        var actualVacancy = vacancyArgumentCaptor.getValue();
        var actualFileDto = fileDtoArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/vacancies");
        assertThat(actualVacancy).isEqualTo(vacancy);
        assertThat(actualFileDto).usingRecursiveComparison().isEqualTo(fileDto);
    }

    @Test
    public void whenSomeExceptionThrownThenGetErrorPageWithMessage() {
        var expectedException = new RuntimeException("Failed to write file");
        when(vacancyService.save(any(), any())).thenThrow(expectedException);

        var model = new ConcurrentModel();
        var view = vacancyController.createVacancy(new Vacancy(), testFile, model);
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }

    @Test
    public void whenRequestPageGetOneVacancyOfIdThenGetOneVacancy() {
        var expectCities = List.of(new City(1, "city1"),
                new City(2, "city2"));
        int id = 5;
        var expectOptionalVacancy = Optional.of(
                new Vacancy(id, "title5", "disc5", false, 3, 4));
        when(cityService.findAll()).thenReturn(expectCities);
        when(vacancyService.findById(id)).thenReturn(expectOptionalVacancy);

        var model = new ConcurrentModel();
        var view = vacancyController.getByIdVacancy(model, id);
        var actualCities = model.getAttribute("cities");
        var actualOptionalVacancy = model.getAttribute("vacancy");

        assertThat(view).isEqualTo("vacancies/one");
        assertThat(actualCities).isEqualTo(expectCities);
        assertThat(actualOptionalVacancy)
                .usingRecursiveComparison()
                .isEqualTo(expectOptionalVacancy.get());
    }

    @Test
    public void whenRequestPageGetOneVacancyOfIdThenGetErrorPage() {
        int id = 0;
        var expectMessage = "Вакансия с указанным идентификатором не найдена";
        when(vacancyService.findById(id)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = vacancyController.getByIdVacancy(model, id);
        var errorMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(errorMessage).isEqualTo(expectMessage);
    }

    @Test
    public void whenPostUpdateVacancyWithThenSameDataAndRedirectToVacanciesPage() throws IOException {
        var vacancy = new Vacancy(1, "test1", "desc1", true, 1, 2);
        var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        var vacancyArgumentCaptor = ArgumentCaptor.forClass(Vacancy.class);
        var filDtoArgumentCaptor = ArgumentCaptor.forClass(FileDto.class);
        when(vacancyService.update(vacancyArgumentCaptor.capture(), filDtoArgumentCaptor.capture()))
                .thenReturn(true);

        var model = new ConcurrentModel();
        var view = vacancyController.updateVacancy(vacancy, testFile, model);
        var actualVacancy = vacancyArgumentCaptor.getValue();
        var actualFileDto = filDtoArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/vacancies");
        assertThat(actualVacancy).isEqualTo(vacancy);
        assertThat(actualFileDto)
                .usingRecursiveComparison()
                .isEqualTo(fileDto);
    }

    @Test
    public void whenPostUpdateVacancyWithUpdateFalseThenReturnErrorPage() {
        var expectMessage = "Вакансия с указанным идентификатором не найдена";
        when(vacancyService.update(any(), any())).thenReturn(false);

        var model = new ConcurrentModel();
        var view = vacancyController.updateVacancy(new Vacancy(), testFile, model);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo(expectMessage);
    }

    @Test
    public void whenPostUpdateVacancySomeExceptionThrownThenGetErrorPageWithMessage() {
        var expectedException = new RuntimeException("Failed to write file");
        when(vacancyService.update(any(), any())).thenThrow(expectedException);

        var model = new ConcurrentModel();
        var view = vacancyController.updateVacancy(new Vacancy(), testFile, model);
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }

    @Test
    public void whenPostDeleteVacancyByIdThenRedirectVacanciesPage() {
        int id = 1;
        when(vacancyService.deleteById(id)).thenReturn(true);

        var view = vacancyController.delete(any(), id);

        assertThat(view).isEqualTo("redirect:/vacancies");
    }

    @Test
    public void whenPostDeleteVacancyByIdThenReturnErrorsPage() {
        var expectMessage = "Вакансия с указанным идентификатором не найдена";
        when(vacancyService.deleteById(anyInt())).thenReturn(false);

        var model = new ConcurrentModel();
        var view = vacancyController.delete(model, anyInt());
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo(expectMessage);
    }
}