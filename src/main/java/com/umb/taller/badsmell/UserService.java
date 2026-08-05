package com.umb.taller.badsmell;

import com.umb.taller.validation.Validator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;
    private final Validator<String> nonEmptyValidator;
    private final Validator<String> emailValidator;
    private final Validator<String> idValidator;

    public UserService() {
        this(new InMemoryUserRepository());
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.nonEmptyValidator = value -> value != null && !value.trim().isEmpty();
        this.emailValidator = value -> value != null && value.contains("@") && value.contains(".");
        this.idValidator = nonEmptyValidator;
    }

    public User createUser(String name, String email) {
        validateName(name);
        validateEmail(email);

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        User user = new User(UUID.randomUUID().toString(), name.trim(), email.trim());
        userRepository.save(user);
        return user;
    }

    public Optional<User> findUser(String id) {
        validateId(id);
        return userRepository.findById(id);
    }

    public Optional<User> findUserByEmail(String email) {
        validateEmail(email);
        return userRepository.findByEmail(email);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String id, String name, String email) {
        validateId(id);
        validateName(name);
        validateEmail(email);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Optional<User> userWithEmail = userRepository.findByEmail(email);
        if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        existingUser.setName(name.trim());
        existingUser.setEmail(email.trim());
        userRepository.save(existingUser);
        return existingUser;
    }

    public void deleteUser(String id) {
        validateId(id);
        userRepository.deleteById(id);
    }

    private void validateName(String name) {
        if (!nonEmptyValidator.validate(name)) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
    }

    private void validateEmail(String email) {
        if (!emailValidator.validate(email)) {
            throw new IllegalArgumentException("El correo no tiene un formato válido");
        }
    }

    private void validateId(String id) {
        if (!idValidator.validate(id)) {
            throw new IllegalArgumentException("El id es obligatorio");
        }
    }
}
