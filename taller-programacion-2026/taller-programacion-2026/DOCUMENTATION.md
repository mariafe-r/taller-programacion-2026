# Documentación de GitHub Copilot - Sistema de Biblioteca

## 1. Clase Person y Author (Herencia)

### Prompt
"Genera una clase Author que herede de Person. Person tiene: name, email, phone. Author tiene biography adicional."

### Código generado
El código completo de Person y Author fue generado correctamente.

### Cambios realizados
1. Se separó Person y Author en archivos diferentes (mejor organización)
2. Se añadió validación en setBiography para que no pueda ser vacío
3. Se añadió anotaciones @Override

### Pilares POO demostrados
- Herencia (Author extends Person)
- Encapsulamiento (atributos private)
- Polimorfismo (toString sobreescrito)


## 2. Clase Book

### Prompt
"Genera una clase Book con atributos: title, author, isbn, yearPublished, isAvailable, maxLoanDays. Incluye validaciones."

### Código generado
Copilot generó la estructura básica con validaciones de ISBN y año.

### Cambios realizados
1. Se añadió métodos loan() y returnBook() para gestionar disponibilidad
2. Se añadió validación adicional en maxLoanDays (1-30 días)
3. Mejora en el formato de toString()

### Pilares POO demostrados
- Encapsulamiento (atributos private)
- Composición (Book tiene Author)


## 3. Interfaz Loanable

### Prompt
"Genera una interfaz Loanable con métodos: borrow(), returnItem(), isAvailable(), getMaxLoanDays()"

### Código generado
Copilot generó la interfaz correctamente.

### Cambios realizados
1. Se implementó que Book implemente Loanable
2. Se conectaron los métodos de la interfaz con los métodos existentes

### Pilares POO demostrados
- Abstracción (interfaz Loanable)
- Polimorfismo (Book implementa Loanable)


## 4. Clase Member

### Prompt
"Genera una clase Member que extienda Person con atributos: memberId, active, borrowedBooks, maxBooksAllowed. Incluye métodos borrowBook y returnBook."

### Código generado
Copilot generó la estructura completa con validaciones.

### Cambios realizados
1. Se mejoró la validación en borrowBook (control de límites)
2. Se añadió inmutabilidad en getBorrowedBooks (retorna copia)
3. Se añadió verificaciones de null

### Pilares POO demostrados
- Herencia (Member extends Person)
- Encapsulamiento (atributos private)
- Composición (List<Book>)


## 5. Clase Loan

### Prompt
"Genera una clase Loan con book, member, loanDate, returnDate, returned. Método para calcular multa."

### Código generado
Copilot generó la clase con cálculo de multas usando LocalDate.

### Cambios realizados
1. Se añadió validación para no devolver dos veces
2. Se ajustó el cálculo de multas para usar días hábiles
3. Se añadieron mensajes de error más descriptivos

### Pilares POO demostrados
- Encapsulamiento (atributos private)
- Composición (Loan tiene Book y Member)


## 6. Clase Library

### Prompt
"Genera una clase Library que gestione libros, miembros y préstamos. Incluye métodos para agregar/remover libros y miembros, prestar/devolver libros."

### Código generado
Copilot generó una clase completa con gestión de colecciones y validaciones.

### Cambios realizados
1. Se añadió validación de duplicados en addBook y registerMember
2. Se añadió método getOverdueLoans para identificar préstamos atrasados
3. Se añadió mensajes de error más específicos
4. Se usó Streams para mejorar eficiencia

### Pilares POO demostrados
- Encapsulamiento (atributos private)
- Composición (Library tiene colecciones)


## 7. Estrategia de pruebas (con y sin conexión a internet)

### Contexto
Al ejecutar `gradle test` en el entorno de laboratorio, la resolución de las dependencias de
JUnit 5 y AssertJ (declaradas en `build.gradle`) requiere acceso a Maven Central. En sesiones
sin conexión estable, esto impedía ejecutar las pruebas automatizadas.

### Solución aplicada
Se mantiene `LibraryServiceTest.java` como la suite oficial en JUnit 5 (se ejecuta con
`gradle test` cuando hay internet), y adicionalmente se creó `SimpleLibraryServiceTest.java`:
una clase con un método `main` que reproduce los mismos siete casos de prueba usando
aserciones propias (`try/catch` + contadores de `passed`/`failed`), sin depender de ninguna
librería externa. Esta clase puede compilarse y ejecutarse únicamente con `javac`/`java`
(ver `scripts/run-simple-tests.sh`), lo que garantiza que el sistema quede validado
funcionalmente incluso sin acceso a internet.

### Pilares POO / SOLID demostrados
- Encapsulamiento y reutilización de la lógica de dominio ya validada por `LibraryService`
- Evidencia de que la lógica de negocio (excepciones, reglas de préstamo) es independiente
  del framework de pruebas utilizado

## 8. Interfaces de usuario (consola interactiva y GUI)

### Prompt
"Genera un menú interactivo de consola y una interfaz gráfica en Swing para operar
LibraryService (listar/agregar libros, registrar miembros, prestar y devolver libros,
ver préstamos vencidos), sin duplicar las reglas de negocio del dominio."

### Código generado
Se creó `com.umb.taller.console.ConsoleMenu` (menú de texto con `Scanner`) y
`com.umb.taller.gui.LibraryGUI` (ventana Swing con pestañas Libros/Miembros/Préstamos,
tablas `JTable` y formularios).

### Cambios realizados
1. Se amplió `LibraryService` con métodos de fachada (`addBook`, `registerMember`,
   `getAllBooks`, `getActiveLoans`, `getOverdueLoans`, `returnBook`) para que ninguna capa
   de presentación acceda directamente a `Library` ni construya entidades del dominio con
   reglas propias.
2. Ambas interfaces (consola y GUI) capturan las mismas excepciones de negocio
   (`ValidationException`, `EntityNotFoundException`, `BusinessRuleException`,
   `IllegalArgumentException`, `IllegalStateException`) y las traducen a mensajes legibles,
   en vez de dejarlas propagar como *stack traces*.
3. Se agregaron tareas de Gradle (`runConsole`, `runGui`) y scripts (`scripts/run-console.sh`,
   `scripts/run-gui.sh`) para poder ejecutarlas con o sin conexión a internet.

### Pilares POO / SOLID demostrados
- **Dependency Inversion (D):** las tres interfaces de usuario (`Main`, `ConsoleMenu`,
  `LibraryGUI`) dependen únicamente de la abstracción `LibraryService`, no del dominio
  interno de `Library`.
- **Single Responsibility (S):** `ConsoleMenu` solo gestiona entrada/salida por texto;
  `LibraryGUI` solo gestiona componentes Swing; ninguna valida reglas de negocio por su
  cuenta.
- **Reutilización:** la misma capa de servicio y dominio se reutiliza sin cambios en las
  tres interfaces, evidenciando que la lógica de negocio es independiente de la interfaz.

## Resumen de Pilares POO Demostrados

Encapsulamiento: Todas las clases (atributos private)
Herencia: Author y Member (extienden Person) 
Polimorfismo: Author y Member (toString sobreescrito) 
Abstracción: Interfaz Loanable 
Composición: Book, Loan, Library (tienen objetos de otras clases) 
