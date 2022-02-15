package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.InitMeals;

import java.util.ArrayList;
import java.util.List;

public class MealData {
    private static final List<Meal> mealsList = new ArrayList<>();

    public static void addMeal(Meal meal) {
        mealsList.add(meal);
    }

    public static void deleteMeal(int id) {
        for (int i = 0; i < mealsList.size(); i++) {
            if (mealsList.get(i).getId() == id) {
                mealsList.remove(i);
                break;
            }
        }
    }

    public static void updateMeal(Meal meal) {
        mealsList.set(mealsList.indexOf(meal), meal);
    }

    public static List<Meal> getAllMeals() {
        return mealsList;
    }

    public static void addMealList(List<Meal> list) {
        mealsList.addAll(list);
    }
}
