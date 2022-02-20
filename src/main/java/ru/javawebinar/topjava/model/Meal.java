package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal extends AbstractNamedEntity {

    private final Integer userId;

    private final LocalDateTime dateTime;

    private final int calories;

    public Meal(LocalDateTime dateTime, String description, int calories, Integer userId) {
        this(null, dateTime, description, calories, userId);
    }

    public Meal(Integer id, LocalDateTime dateTime, String name, int calories, Integer userId) {
        super(id, name);
        this.dateTime = dateTime;
        this.calories = calories;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public boolean isNew() {
        return id == null;
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + name + '\'' +
                ", calories=" + calories +
                '}';
    }
}
