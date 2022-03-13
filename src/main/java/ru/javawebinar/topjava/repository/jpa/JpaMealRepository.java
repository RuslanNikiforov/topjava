package ru.javawebinar.topjava.repository.jpa;

import org.hsqldb.lib.Collection;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {

        if(meal.isNew()) {
            User user = new User();
            user.setId(userId);
            meal.setUser(user);
            em.persist(meal);
            meal.setId(meal.getId());
            return meal;
        }
        else {
             if(em.createNamedQuery(Meal.UPDATE).
                     setParameter(1, meal.getDateTime()).
                     setParameter(2, meal.getCalories()).
                     setParameter(3, meal.getDescription()).
                     setParameter(4, userId).
                     setParameter(5, meal.getId()).executeUpdate() != 0) {
                 return meal;
             }
             else {
                 return null;
             }


        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE).setParameter("user_id", userId).setParameter("id", id).
                executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
         List<Meal> meals = em.createNamedQuery(Meal.GET, Meal.class).
                setParameter("user_id", userId).
                setParameter("id", id).
                getResultList();
         return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> allUserMeals = em.createNamedQuery(Meal.ALL_SORTED, Meal.class).
                setParameter("user_id", userId).
                getResultList();
        Collections.reverse(allUserMeals);
        return allUserMeals;
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        List<Meal> filteredMeals = em.createNamedQuery(Meal.ALL_FILTERED_BY_TIME, Meal.class).
                setParameter(1, startDateTime).
                setParameter(2, endDateTime).
                setParameter(3, userId).
                getResultList();
        Collections.reverse(filteredMeals);
        return filteredMeals;
    }
}