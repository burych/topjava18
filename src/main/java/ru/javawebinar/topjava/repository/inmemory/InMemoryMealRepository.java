package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal ->save(SecurityUtil.authUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        if (meal.getUserId() != userId){
            return null;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        if (get(userId, id) == null){
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        Meal meal = repository.get(id);
        if (userId != meal.getUserId()){
            return  null;
        }
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream().
                filter( meal -> meal.getUserId() == userId).sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());
    }
}

