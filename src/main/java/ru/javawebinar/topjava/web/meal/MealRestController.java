package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;


    public List<Meal> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll userId = " + userId);
        return service.getAll(userId);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get {}", id);
        return service.get(userId,id);
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(userId, meal);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete {}", id);
        service.delete(userId, id);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(userId, meal);
    }

    public List<MealTo> getMealTo(int caloriesPerDay, LocalTime startTime, LocalTime endTime){
        int userId = SecurityUtil.authUserId();
        log.info("convert to MealTo userId = " + userId);
        return MealsUtil.getFilteredTos(getAll(), caloriesPerDay, startTime, endTime);
    }
}