package com.umb.taller.gui;

import com.umb.taller.domain.Book;
import com.umb.taller.domain.Library;
import com.umb.taller.domain.Loan;
import com.umb.taller.domain.Member;
import com.umb.taller.service.LibraryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Interfaz gráfica de escritorio (Java Swing) para el Sistema de Gestión de
 * Biblioteca. Usa exclusivamente {@link LibraryService} para operar sobre el
 * dominio, por lo que toda la lógica de negocio y las validaciones se
 * reutilizan tal cual de la capa de servicio (sin duplicar reglas en la UI).
 *
 * No requiere ninguna librería externa: Swing forma parte del JDK estándar,
 * por lo que esta ventana se puede compilar y ejecutar sin conexión a
 * internet (ver scripts/run-gui.sh). Sí requiere un entorno gráfico
 * (escritorio/X11); no funciona en una terminal remota sin servidor X.
 */
public class LibraryGUI extends JFrame {

    private final LibraryService libraryService;

    private DefaultTableModel booksTableModel;
    private DefaultTableModel membersTableModel;
    private DefaultTableModel loansTableModel;

    private JTable booksTable;
    private JTable membersTable;
    private JTable loansTable;

    private JComboBox<Book> bookComboBox;
    private JComboBox<Member> memberComboBox;

    private JLabel statusLabel;

    public LibraryGUI(LibraryService libraryService) {
        super("📚 Sistema de Gestión de Biblioteca - " + libraryService.getLibraryName());
        this.libraryService = libraryService;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Libros", buildBooksPanel());
        tabbedPane.addTab("Miembros", buildMembersPanel());
        tabbedPane.addTab("Préstamos", buildLoansPanel());

        add(tabbedPane, BorderLayout.CENTER);

        statusLabel = new JLabel(" Listo.");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        add(statusLabel, BorderLayout.SOUTH);

        refreshAll();
    }

    // ---------------------------------------------------------------
    // Panel: Libros
    // ---------------------------------------------------------------
    private JPanel buildBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField isbnField = new JTextField();
        JTextField yearField = new JTextField();

        JPanel form = new JPanel(new GridLayout(1, 0, 6, 6));
        form.add(labeled("Título:", titleField));
        form.add(labeled("Autor:", authorField));
        form.add(labeled("ISBN (13 dígitos):", isbnField));
        form.add(labeled("Año:", yearField));

        JButton addButton = new JButton("Agregar libro");
        addButton.addActionListener(e -> {
            try {
                int year = Integer.parseInt(yearField.getText().trim());
                Book book = libraryService.addBook(
                        titleField.getText(), authorField.getText(), isbnField.getText().trim(), year);
                setStatus("✅ Libro agregado: " + book.getTitle(), false);
                titleField.setText("");
                authorField.setText("");
                isbnField.setText("");
                yearField.setText("");
                refreshAll();
            } catch (NumberFormatException ex) {
                setStatus("❌ El año debe ser un número entero.", true);
            } catch (RuntimeException ex) {
                setStatus("❌ Error: " + ex.getMessage(), true);
            }
        });

        JPanel top = new JPanel(new BorderLayout(6, 6));
        top.add(form, BorderLayout.CENTER);
        top.add(addButton, BorderLayout.EAST);

        booksTableModel = new DefaultTableModel(
                new Object[]{"ISBN", "Título", "Autor", "Año", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        booksTable = new JTable(booksTableModel);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(booksTable), BorderLayout.CENTER);
        return panel;
    }

    // ---------------------------------------------------------------
    // Panel: Miembros
    // ---------------------------------------------------------------
    private JPanel buildMembersPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField memberIdField = new JTextField();

        JPanel form = new JPanel(new GridLayout(1, 0, 6, 6));
        form.add(labeled("Nombre:", nameField));
        form.add(labeled("Correo:", emailField));
        form.add(labeled("Teléfono:", phoneField));
        form.add(labeled("ID miembro:", memberIdField));

        JButton addButton = new JButton("Registrar miembro");
        addButton.addActionListener(e -> {
            try {
                Member member = libraryService.registerMember(
                        nameField.getText(), emailField.getText(), phoneField.getText(),
                        memberIdField.getText().trim());
                setStatus("✅ Miembro registrado: " + member.getName(), false);
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                memberIdField.setText("");
                refreshAll();
            } catch (RuntimeException ex) {
                setStatus("❌ Error: " + ex.getMessage(), true);
            }
        });

        JPanel top = new JPanel(new BorderLayout(6, 6));
        top.add(form, BorderLayout.CENTER);
        top.add(addButton, BorderLayout.EAST);

        membersTableModel = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Correo", "Teléfono", "Libros prestados"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        membersTable = new JTable(membersTableModel);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(membersTable), BorderLayout.CENTER);
        return panel;
    }

    // ---------------------------------------------------------------
    // Panel: Préstamos
    // ---------------------------------------------------------------
    private JPanel buildLoansPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        bookComboBox = new JComboBox<>();
        bookComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                            boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Book) {
                    Book book = (Book) value;
                    setText(book.getIsbn() + " - " + book.getTitle());
                }
                return this;
            }
        });

        memberComboBox = new JComboBox<>();
        memberComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                            boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Member) {
                    Member member = (Member) value;
                    setText(member.getMemberId() + " - " + member.getName());
                }
                return this;
            }
        });

        JButton loanButton = new JButton("Prestar libro");
        loanButton.addActionListener(e -> {
            Book selectedBook = (Book) bookComboBox.getSelectedItem();
            Member selectedMember = (Member) memberComboBox.getSelectedItem();
            if (selectedBook == null || selectedMember == null) {
                setStatus("⚠️  Seleccione un libro disponible y un miembro.", true);
                return;
            }
            try {
                Loan loan = libraryService.loanBook(selectedBook.getIsbn(), selectedMember.getMemberId());
                setStatus("✅ Préstamo realizado: \"" + loan.getBook().getTitle() + "\" → "
                        + loan.getMember().getName(), false);
                refreshAll();
            } catch (RuntimeException ex) {
                setStatus("❌ Error: " + ex.getMessage(), true);
            }
        });

        JPanel form = new JPanel(new GridLayout(1, 0, 6, 6));
        form.add(labeled("Libro disponible:", bookComboBox));
        form.add(labeled("Miembro:", memberComboBox));

        JPanel top = new JPanel(new BorderLayout(6, 6));
        top.add(form, BorderLayout.CENTER);
        top.add(loanButton, BorderLayout.EAST);

        loansTableModel = new DefaultTableModel(
                new Object[]{"Libro", "Miembro", "Fecha préstamo", "Vencido"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loansTable = new JTable(loansTableModel);

        JButton returnButton = new JButton("Devolver libro seleccionado");
        returnButton.addActionListener(e -> {
            int row = loansTable.getSelectedRow();
            List<Loan> activeLoans = libraryService.getActiveLoans();
            if (row < 0 || row >= activeLoans.size()) {
                setStatus("⚠️  Seleccione un préstamo de la tabla para devolver.", true);
                return;
            }
            try {
                Loan loan = activeLoans.get(row);
                libraryService.returnBook(loan);
                setStatus("✅ Libro devuelto: " + loan.getBook().getTitle(), false);
                refreshAll();
            } catch (RuntimeException ex) {
                setStatus("❌ Error: " + ex.getMessage(), true);
            }
        });

        JPanel center = new JPanel(new BorderLayout(6, 6));
        center.add(new JScrollPane(loansTable), BorderLayout.CENTER);
        center.add(returnButton, BorderLayout.SOUTH);

        panel.add(top, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // ---------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------
    private JPanel labeled(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(2, 2));
        panel.add(new JLabel(label), BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void setStatus(String message, boolean error) {
        statusLabel.setText(" " + message);
        statusLabel.setForeground(error ? new Color(170, 0, 0) : new Color(0, 110, 0));
    }

    private void refreshAll() {
        refreshBooksTable();
        refreshMembersTable();
        refreshLoansTable();
        refreshCombos();
    }

    private void refreshBooksTable() {
        booksTableModel.setRowCount(0);
        for (Book book : libraryService.getAllBooks()) {
            booksTableModel.addRow(new Object[]{
                    book.getIsbn(), book.getTitle(), book.getAuthor().getName(),
                    book.getYearPublished(), book.isAvailable() ? "Disponible" : "Prestado"
            });
        }
    }

    private void refreshMembersTable() {
        membersTableModel.setRowCount(0);
        for (Member member : libraryService.getAllMembers()) {
            membersTableModel.addRow(new Object[]{
                    member.getMemberId(), member.getName(), member.getEmail(), member.getPhone(),
                    member.getBorrowedBooks().size() + "/" + member.getMaxBooksAllowed()
            });
        }
    }

    private void refreshLoansTable() {
        loansTableModel.setRowCount(0);
        List<Loan> overdue = libraryService.getOverdueLoans();
        for (Loan loan : libraryService.getActiveLoans()) {
            loansTableModel.addRow(new Object[]{
                    loan.getBook().getTitle(), loan.getMember().getName(),
                    loan.getLoanDate().toString(), overdue.contains(loan) ? "Sí" : "No"
            });
        }
    }

    private void refreshCombos() {
        bookComboBox.removeAllItems();
        for (Book book : libraryService.getAvailableBooks()) {
            bookComboBox.addItem(book);
        }
        memberComboBox.removeAllItems();
        for (Member member : libraryService.getAllMembers()) {
            memberComboBox.addItem(member);
        }
    }

    // ---------------------------------------------------------------
    // Entry point
    // ---------------------------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Si no se puede aplicar el look and feel del sistema, se usa el predeterminado.
            }

            Library library = new Library("Biblioteca UMB");
            LibraryService service = new LibraryService(library);
            seedSampleData(service);

            new LibraryGUI(service).setVisible(true);
        });
    }

    private static void seedSampleData(LibraryService service) {
        try {
            service.addBook("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "9780747532699", 1997);
            service.addBook("Cien Años de Soledad", "Gabriel García Márquez", "9788437604947", 1967);
            service.addBook("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "9780439064866", 1998);
            service.registerMember("Carlos Pérez", "carlos@umb.edu.co", "3101234567", "M001");
        } catch (RuntimeException e) {
            System.err.println("No se pudieron cargar los datos de ejemplo: " + e.getMessage());
        }
    }
}
