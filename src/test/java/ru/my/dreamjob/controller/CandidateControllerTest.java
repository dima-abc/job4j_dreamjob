package ru.my.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;
import ru.my.dreamjob.model.Candidate;
import ru.my.dreamjob.model.City;
import ru.my.dreamjob.model.dto.FileDto;
import ru.my.dreamjob.service.CandidateService;
import ru.my.dreamjob.service.CityService;

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
 * Test CandidateController
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 05.02.2023
 */
public class CandidateControllerTest {
    private CandidateService candidateService;
    private CityService cityService;
    private CandidateController candidateController;
    private MultipartFile testFile;

    @BeforeEach
    public void initService() {
        candidateService = mock(CandidateService.class);
        cityService = mock(CityService.class);
        candidateController = new CandidateController(candidateService, cityService);
        testFile = new MockMultipartFile("testFile.img", new byte[]{1, 2, 3});
    }

    @Test
    public void whenRequestCandidateListPageThenGetPageWithCandidates() {
        var candidate1 = new Candidate(1, "name1", "desc1", 1, 2);
        var candidate2 = new Candidate(2, "name2", "desc2", 3, 4);
        var expectedCandidates = List.of(candidate1, candidate2);
        when(candidateService.findAll()).thenReturn(expectedCandidates);

        var model = new ConcurrentModel();
        var view = candidateController.getAllCandidate(model);
        var actualCandidates = model.getAttribute("candidates");

        assertThat(view).isEqualTo("candidates/list");
        assertThat(actualCandidates).usingRecursiveComparison().isEqualTo(expectedCandidates);
    }

    @Test
    public void whenRequestGetCreationPageCandidateThenGetPageCreationCandidateAndGetAllCities() {
        var city1 = new City(1, "city1");
        var city2 = new City(2, "city2");
        var expectedCities = List.of(city1, city2);
        when(cityService.findAll()).thenReturn(expectedCities);

        var model = new ConcurrentModel();
        var view = candidateController.getCreationPageCandidate(model);
        var actualCities = model.getAttribute("cities");

        assertThat(view).isEqualTo("candidates/create");
        assertThat(actualCities).usingRecursiveComparison().isEqualTo(expectedCities);
    }

    @Test
    public void whenPostCandidateWithFileSameDataAndRedirectToCandidatesPage() throws IOException {
        var candidate = new Candidate(1, "name1", "desc1", 2, 4);
        var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        var candidateArgumentCaptor = ArgumentCaptor.forClass(Candidate.class);
        var fileDtoArgumentCaptor = ArgumentCaptor.forClass(FileDto.class);
        when(candidateService.save(candidateArgumentCaptor.capture(), fileDtoArgumentCaptor.capture()))
                .thenReturn(candidate);

        var model = new ConcurrentModel();
        var view = candidateController.createCandidate(candidate, testFile, model);
        var actualCandidate = candidateArgumentCaptor.getValue();
        var actualFileDto = fileDtoArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/candidates");
        assertThat(actualCandidate).usingRecursiveComparison().isEqualTo(candidate);
        assertThat(actualFileDto).usingRecursiveComparison().isEqualTo(fileDto);
    }

    @Test
    public void whenSomeExceptionThrowThenGetErrorPageWithMessage() {
        var expectedException = new RuntimeException("Failed to write file");
        when(candidateService.save(any(), any())).thenThrow(expectedException);

        var model = new ConcurrentModel();
        var view = candidateController.createCandidate(new Candidate(), testFile, model);
        var actualException = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualException).isEqualTo(expectedException.getMessage());
    }

    @Test
    public void whenRequestPageGerOneCandidateByIdThenGetOneCandidate() {
        int idCandidate = 1;
        var expectedCities = List.of(new City(1, "city1"), new City(2, "city2"));
        var expectedCandidate = new Candidate(idCandidate, "name1", "desc1", 1, 1);
        when(cityService.findAll()).thenReturn(expectedCities);
        when(candidateService.findById(idCandidate)).thenReturn(Optional.of(expectedCandidate));


        var model = new ConcurrentModel();
        var view = candidateController.getByIdCandidate(model, idCandidate);
        var actualCities = model.getAttribute("cities");
        var actualCandidate = model.getAttribute("candidate");

        assertThat(view).isEqualTo("candidates/one");
        assertThat(actualCities).usingRecursiveComparison().isEqualTo(expectedCities);
        assertThat(actualCandidate).usingRecursiveComparison().isEqualTo(expectedCandidate);
    }

    @Test
    public void whenRequestPageGetOneCandidateThenGetErrorPageWithMessage() {
        var expectedMessage = "Кандидат с указанным идентификатором не найдена";
        when(candidateService.findById(anyInt())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = candidateController.getByIdCandidate(model, anyInt());
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    public void whenRequestPostUpdateCandidateWithThenSameDataAndRedirectCandidatesToCandidatesPage() throws IOException {
        var candidate = new Candidate(1, "name1", "desc1", 1, 2);
        var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        var candidatesArgumentCaptor = ArgumentCaptor.forClass(Candidate.class);
        var fileDtoArgumentCaptor = ArgumentCaptor.forClass(FileDto.class);
        when(candidateService.update(candidatesArgumentCaptor.capture(), fileDtoArgumentCaptor.capture()))
                .thenReturn(true);

        var model = new ConcurrentModel();
        var view = candidateController.updateCandidate(candidate, testFile, model);
        var actualFileDto = fileDtoArgumentCaptor.getValue();
        var actualCandidate = candidatesArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/candidates");
        assertThat(actualFileDto).usingRecursiveComparison().isEqualTo(fileDto);
        assertThat(actualCandidate).usingRecursiveComparison().isEqualTo(candidate);
    }

    @Test
    public void whenRequestPostUpdateCandidateThenGetErrorPage() {
        var expectedMessage = "Кандидат с указанным идентификатором не найдена";
        when(candidateService.update(any(), any()))
                .thenReturn(false);

        var model = new ConcurrentModel();
        var view = candidateController.updateCandidate(new Candidate(), testFile, model);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    public void whenPostUpdateCandidateSomeExceptionThrowGetErrorPageWithMessage() {
        var expectedException = new RuntimeException("Filed to write file");
        when(candidateService.update(any(), any())).thenThrow(expectedException);

        var model = new ConcurrentModel();
        var view = candidateController.updateCandidate(new Candidate(), testFile, model);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo(expectedException.getMessage());
    }

    @Test
    public void whenRequestPostDeleteCandidateByIdThenGetRedirectPageCandidates() {
        when(candidateService.deleteById(anyInt())).thenReturn(true);

        var model = new ConcurrentModel();
        var view = candidateController.delete(model, anyInt());

        assertThat(view).isEqualTo("redirect:/candidates");
    }

    @Test
    public void whenRequestPostDeleteCandidateByIdThenGetErrorPageAndSameMessage() {
        var expectedMessage = "Кандидат с указанным идентификатором не найдена";
        when(candidateService.deleteById(anyInt())).thenReturn(false);

        var model = new ConcurrentModel();
        var view = candidateController.delete(model, anyInt());
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}