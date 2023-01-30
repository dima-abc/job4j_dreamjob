package ru.my.dreamjob.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.my.dreamjob.model.File;
import ru.my.dreamjob.model.dto.FileDto;
import ru.my.dreamjob.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.5. Формы
 * 4. Формы. Загрузка файла на сервер. [#504855 #284295]
 * SimpleFileService реализация слоя бизнес логики управления File
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 30.01.2023
 */
@Service
public class SimpleFileService implements FileService {
    private final FileRepository fileRepository;

    private final String storageDirectory;

    public SimpleFileService(FileRepository fileRepository,
                             @Value("${file.directory}") String storageDirectory) {
        this.fileRepository = fileRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    /**
     * Создание директории с полным путем подкаталогов к ней, если ее еще нет.
     *
     * @param path directory name
     */
    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName.
     * Так создается уникальный путь для нового файла.
     * UUID это просто рандомная строка определенного формата;
     *
     * @param sourceName File name
     * @return random source.
     */
    private String getNewFilePath(String sourceName) {
        return storageDirectory
                + java.io.File.separator
                + UUID.randomUUID()
                + sourceName;
    }

    /**
     * Записывает массив байт в файл
     *
     * @param path    File name
     * @param content Array byte
     */
    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Чтение файла в массив байт.
     *
     * @param path File name
     * @return Array byte
     */
    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление файла из каталога файловой системы.
     *
     * @param path File name
     */
    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File save(FileDto fileDto) {
        var path = getNewFilePath(fileDto.getName());
        writeFileBytes(path, fileDto.getContent());
        return fileRepository.save(new File(fileDto.getName(), path));
    }

    @Override
    public Optional<FileDto> getFileById(int id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }

    @Override
    public boolean deleteById(int id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return false;
        }
        deleteFile(fileOptional.get().getPath());
        return fileRepository.deleteById(id);
    }
}
