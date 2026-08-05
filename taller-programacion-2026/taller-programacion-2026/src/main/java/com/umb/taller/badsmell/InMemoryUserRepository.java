package com.umb.taller.badsmell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> usersById = new HashMap<>();
    private final Map<String, String> emailToId = new HashMap<>();

    @Override
    public User save(User user) {
        usersById.put(user.getId(), user);
        emailToId.put(user.getEmail().toLowerCase(), user.getId());
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(usersById.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String normalizedEmail = email.toLowerCase();
        String id = emailToId.get(normalizedEmail);
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(usersById.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(usersById.values());
    }

    @Override
    public void deleteById(String id) {
        User removed = usersById.remove(id);
        if (removed != null) {
            emailToId.remove(removed.getEmail().toLowerCase());
        }
    }

    @Override
    public boolean existsById(String id) {
        return usersById.containsKey(id);
    }

    @Override
    public long count() {
        return usersById.size();
    }
}
