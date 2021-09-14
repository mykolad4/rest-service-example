package com.demchenko.restservice.service;

import com.demchenko.restservice.model.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

@Service
public class UserService {
    private static final ArrayList<User> users = new ArrayList<>();
    private Integer index = 4;

    static {
        users.add(User.builder().id(1).name("Mykola").birthDate(Date.from(Instant.now())).build());
        users.add(User.builder().id(2).name("Tetiana").birthDate(Date.from(Instant.now())).build());
        users.add(User.builder().id(3).name("Varvara").birthDate(Date.from(Instant.now())).build());
        users.add(User.builder().id(4).name("Andriy").birthDate(Date.from(Instant.now())).build());
    }

    public ArrayList<User> findAll() {
        return users;
    }

    public Optional<User> findById(Integer id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public User save(User user) {
        user.setId(++index);
        users.add(user);
        return user;
    }

    public Optional<User> deleteById(Integer id) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId().equals(id)) {
                iterator.remove();
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
