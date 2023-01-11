package ru.my.dreamjob.model;

import java.util.Objects;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 3.2.5. Формы
 * 2. Формы. Списки. [#504854 #284213]
 * City модель данных справочника городов.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 11.01.2023
 */
public class City {
    private int id;
    private String name;

    public City() {
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        City city = (City) o;
        return id == city.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "City{id=" + id + ", name='" + name + '\'' + '}';
    }
}
