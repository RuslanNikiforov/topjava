package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

public class MealTestData {
    public final static Integer MealID_1 = 1;
    public final static Integer MealID_2 = MealID_1 + 1;

    public final static Meal MEAL1 = new Meal(1, LocalDateTime.now(), "Food1", 820);
    public final static Meal MEAL2 = new Meal(2, LocalDateTime.now(), "Food2", 500);
    public final static Meal MEAL3 = new Meal(3, LocalDateTime.now(), "Food3", 400);
    public final static Meal MEAL4 = new Meal(4, LocalDateTime.of(2022, 2,
            24, 0, 0), "Food4", 300);

}
