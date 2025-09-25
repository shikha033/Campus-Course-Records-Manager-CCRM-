
# Campus Course & Records Manager (CCRM)

This project is a console-based Java SE application to manage students, courses, enrollments, grades, and file operations (CSV import/export and backups).
It demonstrates OOP, Java 8+ features (Streams, Date/Time), NIO.2, design patterns (Singleton, Builder), enums, lambdas, recursion, and exception handling.


## Run Windows
java -cp out edu.ccrm.cli.CCRMMain




## Run (Linux/Mac)
javac -d out $(find src -name "*.java")
java -cp out edu.ccrm.cli.CCRMMain



## Notes
- Enable assertions with: java -ea -cp out edu.ccrm.cli.CCRMMain
- Data folder: ./data

See USAGE.md for sample commands and CSV format.
