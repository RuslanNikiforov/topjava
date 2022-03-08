package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))

public class MealServiceTest {

    @Autowired
    MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(MealTestData.MealID_2, UserTestData.ADMIN_ID);
        assertThat(meal).usingRecursiveComparison().ignoringFields("dateTime").
                isEqualTo(MealTestData.MEAL2);
    }

    @Test
    public void delete() {
        mealService.delete(MealTestData.MealID_1, UserTestData.USER_ID);
        assertThrows(NotFoundException.class,
                () -> mealService.delete(MealTestData.MealID_1, UserTestData.USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> filteredMeals = mealService.getBetweenInclusive(LocalDate.of(2022, 3, 5),
                LocalDate.of(2022, 3, 10), UserTestData.USER_ID);
        assertThat(filteredMeals).
                usingRecursiveFieldByFieldElementComparatorIgnoringFields("dateTime").
                isEqualTo(Arrays.asList(MealTestData.MEAL1));
    }

    @Test
    public void getAll() {
        assertThat(mealService.getAll(UserTestData.ADMIN_ID)).
                usingRecursiveFieldByFieldElementComparatorIgnoringFields("dateTime").
                isEqualTo(Arrays.asList(MealTestData.MEAL2, MealTestData.MEAL3));
    }

    @Test
    public void update() {
        Meal updated = new Meal(MealTestData.MEAL1);
        updated.setCalories(300);
        updated.setDescription("updatedMeal");
        mealService.update(updated, UserTestData.USER_ID);
        assertThat(mealService.get(updated.getId(), UserTestData.USER_ID)).
                usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "newFood", 1000);
        newMeal = mealService.create(newMeal, UserTestData.USER_ID);
        assertThat(mealService.get(newMeal.getId(), UserTestData.USER_ID)).
                usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(newMeal);
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.delete(5, UserTestData.USER_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(5, UserTestData.USER_ID));
    }

    @Test
    public void updatedNotFound() {
        Meal notFoundMeal = MealTestData.MEAL1;
        notFoundMeal.setId(10);
        assertThrows(NotFoundException.class, () -> mealService.update(notFoundMeal, UserTestData.USER_ID));
    }
}