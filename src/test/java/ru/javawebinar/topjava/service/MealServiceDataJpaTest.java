package ru.javawebinar.topjava.service;


import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MatcherFactory;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;

@ActiveProfiles(profiles = "datajpa")
public class MealServiceDataJpaTest extends MealServiceTest{
    @Test
    public void getUserWhoOwnsThisMeal() {
        User actual = service.getUserWhoOwnsThisMeal(MealTestData.MEAL1_ID, UserTestData.USER_ID);
        UserTestData.USER_MATCHER.assertMatch(actual, UserTestData.user);
    }
}
