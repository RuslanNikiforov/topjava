package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (userId == meal.getUserId()) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
            // handle case: update, but not present in storage
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        else {
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.get(id) != null) {
            if (repository.get(id).getUserId() == userId) {
                repository.remove(id);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.get(id) != null) {
            if (repository.get(id).getUserId() == userId) {
                return repository.get(id);
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        List<Meal> meals = new ArrayList<>(repository.values());
        return meals.stream().filter(meal -> meal.getUserId() == userId).
                sorted(((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))).collect(Collectors.toList());
    }

    public Collection<Meal> getAllWithoutFilters(int userId) {
        List<Meal> meals = new ArrayList<>(repository.values());
        return meals.stream().filter(meal -> meal.getUserId() == userId).collect(Collectors.toList());
    }

}

