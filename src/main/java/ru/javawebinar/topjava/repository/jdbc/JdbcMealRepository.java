package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JdbcMealRepository implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        insertMeal = new SimpleJdbcInsert(jdbcTemplate).withTableName("meals").
                usingGeneratedKeyColumns("id");
    }



    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().
                addValue("user_id", userId).
                addValue("id", meal.getId()).
                addValue("description", meal.getDescription()).
                addValue("calories", meal.getCalories()).
                addValue("dateTime", meal.getDateTime());
        if (meal.isNew()) {
            meal.setId(insertMeal.executeAndReturnKey(mapSqlParameterSource).intValue());
        } else if (namedParameterJdbcTemplate.update("UPDATE meals " +
                "SET description=:description, calories=:calories," +
                " datetime=:dateTime WHERE user_id=:user_id AND id=:id", mapSqlParameterSource) == 0) {
            return null;
        }
        return meal;
    }


    @Override

        public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE user_id=? AND id=?", userId, id) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
            List<Meal> list = jdbcTemplate.query("SELECT id, datetime, description, calories FROM meals " +
                    "WHERE id=? AND user_id=?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(list);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT id, description, datetime, calories  FROM meals "
                + "WHERE user_id=?", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT id, description, calories, datetime FROM meals " + "WHERE user_id=? ORDER BY datetime DESC", ROW_MAPPER, userId);
        return meals.stream().filter(meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime)).
                collect(Collectors.toList());
    }
}
