# STEPS TO SETUP DB
```bash
CREATE DATABASE kbc_quiz;
USE kbc_quiz;
```
```bash
CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question TEXT NOT NULL,
    option1 VARCHAR(255) NOT NULL,
    option2 VARCHAR(255) NOT NULL,
    option3 VARCHAR(255) NOT NULL,
    option4 VARCHAR(255) NOT NULL,
    correct_option INT NOT NULL
);
```
```bash
INSERT INTO questions (question, option1, option2, option3, option4, correct_option)
VALUES
('Who is known as the father of the Java programming language?', 'James Gosling', 'Bjarne Stroustrup', 'Dennis Ritchie', 'Guido van Rossum', 1),
('Which company originally developed the Java programming language?', 'Microsoft', 'Oracle', 'Sun Microsystems', 'IBM', 3),
('Which of the following is not a feature of Java?', 'Object-Oriented', 'Platform-Dependent', 'Secure', 'Robust', 2),
('Which of the following is not an OOP concept in Java?', 'Encapsulation', 'Inheritance', 'Polymorphism', 'Pointer Arithmetic', 4),
('Which keyword is used to prevent inheritance in Java?', 'private', 'static', 'final', 'protected', 3),
('Which of these is not a primitive data type in Java?', 'int', 'char', 'boolean', 'String', 4),
('Which method is the entry point of any Java program?', 'main()', 'start()', 'init()', 'run()', 1),
('Which loop is guaranteed to execute at least once in Java?', 'for loop', 'while loop', 'do-while loop', 'none of the above', 3),
('Which of these is not a reserved keyword in Java?', 'assert', 'nullptr', 'struct', 'goto', 4),
('What is the size of the int data type in Java?', '16 bytes', '8 bytes', '4 bytes', '2 byte', 3);
```
```bash
INSERT INTO questions (question, option1, option2, option3, option4, correct_option)
VALUES
('Which JDBC driver is known as the "thin driver"?', 'Type-1', 'Type-2', 'Type-3', 'Type-4', 4),
('Which SQL command removes all rows from a table without deleting it?', 'DELETE', 'DROP', 'TRUNCATE', 'CLEAR', 3),
('Which method executes a query returning a ResultSet in JDBC?', 'execute()', 'executeQuery()', 'executeUpdate()', 'executeBatch()', 2),
('Which is not a valid state for a Java thread?', 'NEW', 'RUNNABLE', 'WAITING', 'PROCESSING', 4),
('Which structure follows LIFO order?', 'Queue', 'LinkedList', 'Stack', 'HashMap', 3),
('Which collection preserves insertion order?', 'HashSet', 'TreeSet', 'LinkedHashSet', 'PriorityQueue', 3),
('Which pattern hides object creation logic?', 'Singleton', 'Observer', 'Factory', 'Decorator', 3),
('What handles memory in Java?', 'Pointers', 'Garbage Collection', 'Manual Deallocation', 'Destructors', 2),
('Which sort is most efficient for large data?', 'Bubble Sort', 'Insertion Sort', 'Selection Sort', 'Quick Sort', 2),
('Which exceptions must be caught in Java?', 'Checked', 'All', 'Unchecked', 'None', 1),
('Which SQL keyword returns unique records?', 'SELECT', 'DISTINCT', 'UNIQUE', 'GROUP BY', 2);
```

## AFTER THAT OPEN VS CODE AND PRESS
```
ctrl + shift + d 
 ```
and click run 
the project should run , hopefully 

refrence CLI :-
```bash
PS C:\Users\Acer\Desktop\jyyi> java -cp "C:\Users\Acer\Desktop\jyyi\bin;C:\Users\Acer\Desktop\jyyi\mysql-connector-j-9.0.0.jar" kbc.KBC
```

```bash
& "$env:JAVA_HOME\bin\javac.exe" -d bin src\kbc\*.java
& "$env:JAVA_HOME\bin\java.exe" -cp "bin;C:\Users\ashis\OneDrive\Desktop\kbc-test1\mysql-connector-j-9.0.0.jar" kbc.KBC
$env:JAVA_HOME = "C:\Program Files\Java\jdk-22"
$env:Path += ";$env:JAVA_HOME\bin"

