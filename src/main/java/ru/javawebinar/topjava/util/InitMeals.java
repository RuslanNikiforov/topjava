package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class InitMeals {
    public static List<Meal> getAllMeals() {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.MARCH, 20, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 600),
                new Meal(LocalDateTime.of(2020, Month.MARCH, 20, 13, 0), "Обед", 1200),
                new Meal(LocalDateTime.of(2020, Month.APRIL, 15, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JUNE, 1, 0, 0), "Еда на граничное значение", 900),
                new Meal(LocalDateTime.of(2020, Month.APRIL, 15, 20, 0), "Ужин", 700),
                new Meal(LocalDateTime.of(2020, Month.JUNE, 1, 0, 0), "Еда на граничное значение", 1200),
                new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 25, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.DECEMBER, 7, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.SEPTEMBER, 10, 20, 0), "Ужин", 410)
        );
        return meals;
    }
}
