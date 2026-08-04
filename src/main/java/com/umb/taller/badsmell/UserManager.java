package com.umb.taller.badsmell;

public class UserManager {
    private final UserService userService;
    private final EmailService emailService;
    private final ReportService reportService;
    private final AuditService auditService;

    public UserManager(UserService userService, EmailService emailService,
                       ReportService reportService, AuditService auditService) {
        this.userService = userService;
        this.emailService = emailService;
        this.reportService = reportService;
        this.auditService = auditService;
    }

    public User createUser(String name, String email) {
        User user = userService.createUser(name, email);
        emailService.sendEmail(user.getEmail(), "Bienvenido a nuestra plataforma");
        auditService.logActivity("createUser");
        return user;
    }

    public User updateUser(String id, String name, String email) {
        User user = userService.updateUser(id, name, email);
        auditService.logActivity("updateUser");
        return user;
    }

    public boolean deleteUser(String id) {
        boolean deleted = userService.deleteUser(id);
        auditService.logActivity("deleteUser");
        return deleted;
    }

    public User findUser(String id) {
        return userService.findUser(id).orElse(null);
    }

    public String generateReport() {
        auditService.logActivity("generateReport");
        return reportService.generateUserReport(userService.findAllUsers());
    }
}
