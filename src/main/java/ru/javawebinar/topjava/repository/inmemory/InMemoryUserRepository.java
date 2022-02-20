package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if(user.isNew()) {
            user.setId(counter.incrementAndGet());
            return repository.put(user.getId(), user);
        }
        else {
            return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
        }
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        List<User> users = (List<User>) repository.values();
        Collections.sort(users, (Comparator.comparing(AbstractNamedEntity::getName)));
        return users;
    }

    @Override
    public User getByEmail(String email) {
        return getAll().stream().filter(user -> user.getEmail().equals(email)).findAny().orElse(null);
    }
}













