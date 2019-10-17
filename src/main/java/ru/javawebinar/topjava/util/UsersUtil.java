package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(null, "User1", "User1@mail.ru", "secret", Role.ROLE_USER),
            new User(null, "User2", "User2@mail.ru", "secret", Role.ROLE_USER),
            new User(null, "User77", "User77@mail.ru", "secret", Role.ROLE_ADMIN, Role.ROLE_USER),
            new User(null, "User4", "User4@mail.ru", "secret", Role.ROLE_USER)
    );
}
