package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;

@ActiveProfiles(profiles = "datajpa")
public class UserServiceDataJpaTest extends UserServiceTest {

    @Test
    public void getAllMeals() {
        MealTestData.MEAL_MATCHER.assertMatch(service.getAllMealsOwnedByUser(UserTestData.USER_ID), MealTestData.meals);
    }
}
