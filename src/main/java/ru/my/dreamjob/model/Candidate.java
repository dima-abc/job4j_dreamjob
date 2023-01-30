package ru.my.dreamjob.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 5. Список кандидатов [#504842 #281404]
 * Candidate модель данных кандидатов.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 09.01.2023
 */
public class Candidate {
    private int id;
    private String name;
    private String description;
    private int cityId;
    private int fileId;
    private LocalDateTime createDate = LocalDateTime.now().withNano(0);

    public Candidate() {
    }

    public Candidate(int id,
                     String name,
                     String description,
                     int cityId,
                     int fileId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cityId = cityId;
        this.fileId = fileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Candidate{id=" + id + ", name='" + name + '\'' + ", description='" + description + '\''
                + ", cityId=" + cityId + ", fileId=" + fileId + ", createDate=" + createDate + '}';
    }
}
