package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    int deleteMealByIdAndUser_Id(int id, int userId);

    Meal getMealByIdAndUser_Id(int id, int userId);

    List<Meal> getAllByUser_IdOrderByDateTimeDesc(int userId);

    List<Meal> getAllByUser_IdAndDateTimeGreaterThanEqualAndDateTimeLessThanOrderByDateTimeDesc
            (int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
