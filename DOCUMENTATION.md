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


## Resumen de Pilares POO Demostrados

Encapsulamiento: Todas las clases (atributos private)
Herencia: Author y Member (extienden Person) 
Polimorfismo: Author y Member (toString sobreescrito) 
Abstracción: Interfaz Loanable 
Composición: Book, Loan, Library (tienen objetos de otras clases) 
