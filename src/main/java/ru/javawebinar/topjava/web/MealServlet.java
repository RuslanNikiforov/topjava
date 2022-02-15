package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealData;
import ru.javawebinar.topjava.util.InitMeals;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(MealData.getAllMeals().isEmpty()) {
            MealData.addMealList(InitMeals.getAllMeals());
        }
        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("delete")) {
                MealData.deleteMeal(Integer.parseInt(request.getParameter("id")));
                log.debug("Meal has been deleted");
            }
        }
        List<MealTo> meals = MealsUtil.filteredByStreams(MealData.getAllMeals(), LocalTime.of(0, 0),
                LocalTime.of(23, 59), 1800);

        request.setAttribute("mealsToList", meals);
        log.debug("forward to Meals");
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal;
        if (request.getParameter("action").equals("add")) {
            meal = new Meal(LocalDateTime.parse(request.getParameter("date")), request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
            MealData.addMeal(meal);
            log.debug("Meal has been added");
        }
        List<MealTo> meals = MealsUtil.filteredByStreams(MealData.getAllMeals(), LocalTime.of(0, 0),
                LocalTime.of(23, 59), 1800);
        request.setAttribute("mealsToList", meals);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
