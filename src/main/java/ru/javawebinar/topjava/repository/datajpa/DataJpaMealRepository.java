package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {


    private final CrudMealRepository crudRepository;

    @Autowired
    public DataJpaMealRepository(CrudMealRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        if (meal == null) {
            return null;
        }
        try {
            Meal currentMealInRepository = get(meal.getId(), userId);
            if (currentMealInRepository != null) {
                meal.setUser(currentMealInRepository.getUser());
                crudRepository.save(meal);
            }
            else {
                return null;
            }
        }
        catch (NullPointerException e) {
            User user = new User();
            user.setId(userId);
            meal.setUser(user);
        }
        return crudRepository.save(meal);
    }

    @Transactional()
    public boolean delete(int id, int userId) {
        return crudRepository.deleteMealByIdAndUser_Id(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.getMealByIdAndUser_Id(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAllByUser_IdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getAllByUser_IdAndDateTimeGreaterThanEqualAndDateTimeLessThanOrderByDateTimeDesc
                (userId, startDateTime, endDateTime);
    }


}
