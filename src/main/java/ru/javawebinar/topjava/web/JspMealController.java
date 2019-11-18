package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
public class JspMealController {

    @Autowired
    private MealService service;

    @GetMapping("/meals")
    public String getMeals(HttpServletRequest request, Model model)  {

        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = Integer.parseInt(request.getParameter("id"));
                service.delete(id, SecurityUtil.authUserId());
                //response.sendRedirect("meals");
                return  "redirect:meals";

            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        service.get(Integer.parseInt(request.getParameter("id")), SecurityUtil.authUserId());
                request.setAttribute("meal", meal);
                //request.getRequestDispatcher("mealForm.jsp").forward(request, response);
                return "mealForm";


            //case "all":
            default:
                model.addAttribute("meals", MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
                return "meals";
        }
    }


    @PostMapping("/meals")
    protected String doPost(HttpServletRequest request) {
        //request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            service.create(meal, SecurityUtil.authUserId());
        } else {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            service.update(meal, SecurityUtil.authUserId());
        }
        return "redirect:meals"; //response.sendRedirect("meals");
    }
}
