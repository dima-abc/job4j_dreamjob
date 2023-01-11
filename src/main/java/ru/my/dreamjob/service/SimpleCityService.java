package ru.my.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.my.dreamjob.model.City;
import ru.my.dreamjob.repository.CityRepository;
import ru.my.dreamjob.repository.MemoryCityRepository;

import java.util.Collection;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 3.2.5. Формы
 * 2. Формы. Списки. [#504854 #284213]
 * SimpleCityService реализация слоя сервиса кандидатов.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 11.01.2023
 */
@Service
public class SimpleCityService implements CityService {
    private final CityRepository cityRepository;

    public SimpleCityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Collection<City> findAll() {
        return cityRepository.findAll();
    }
}
