package com.umb.taller.badsmell;

import java.util.List;

public class ReportService {
    public String generateUserReport(List<User> users) {
        if (users == null) {
            throw new IllegalArgumentException("La lista de usuarios no puede ser nula");
        }

        return "User Report: Total users = " + users.size();
    }
}
