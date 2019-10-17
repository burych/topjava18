package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));

            System.out.println(" Meal ");
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll().forEach(System.out::println);

            System.out.println(" Meal filtered ");
            mealRestController.getMealTo(2000, LocalTime.of(7, 0), LocalTime.of(12,0)).forEach(System.out::println);
        }

        /*InMemoryMealRepository inMemoryMealRepository = new InMemoryMealRepository();
        inMemoryMealRepository.getAll(SecurityUtil.authUserId()).stream().forEach(System.out::println);
        System.out.println("delete:");
        inMemoryMealRepository.delete(1, 6);
        inMemoryMealRepository.getAll(SecurityUtil.authUserId()).stream().forEach(System.out::println);*/

    }
}
