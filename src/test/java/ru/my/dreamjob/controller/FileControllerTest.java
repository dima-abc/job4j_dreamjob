package ru.my.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.my.dreamjob.model.dto.FileDto;
import ru.my.dreamjob.service.FileService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.8. Spring Test, Mockito
 * 2. Тестируем VacancyController [#504867 #288430]
 * Test FileControllerTest
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 05.02.2023
 */
public class FileControllerTest {
    private FileService fileService;
    private FileController fileController;

    @BeforeEach
    public void initService() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
    }

    @Test
    public void whenRequestGetFileByIdThenReturnResponseEntityStatusOk() {
        int id = 1;
        var fileDto = new FileDto("file", new byte[]{1, 2, 3});
        when(fileService.getFileById(1)).thenReturn(Optional.of(fileDto));
        var expectedResponseEntity = ResponseEntity.ok(fileDto.getContent());

        var actualResponseEntity = fileController.getById(id);

        assertThat(actualResponseEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponseEntity);
    }

    @Test
    public void whenRequestGetFileByIdThenReturnResponseEntityNotFound() {
        when(fileService.getFileById(anyInt())).thenReturn(Optional.empty());
        var expectedResponseEntity = ResponseEntity.notFound().build();

        var actualResponseEntity = fileController.getById(anyInt());

        assertThat(actualResponseEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponseEntity);
    }

}