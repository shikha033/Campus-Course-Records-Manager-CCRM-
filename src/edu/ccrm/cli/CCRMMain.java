
package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.config.DataStore;
import edu.ccrm.domain.*;
import edu.ccrm.io.*;
import edu.ccrm.service.*;
import edu.ccrm.util.*;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main CLI demonstrating menu, switch, loops, labeled break, recursion, Streams, and more.
 */
public class CCRMMain {
    private static final AppConfig cfg = AppConfig.getInstance();
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    private static final ImportExportService ioService = new ImportExportService();

    public static void main(String[] args) throws Exception {
        seedSampleData();

        boolean running = true;
        outer: while (running) {
            System.out.println("\n==== CCRM MENU ====");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Enrollment / Grades");
            System.out.println("4. Import/Export");
            System.out.println("5. Backup & Size");
            System.out.println("6. Reports");
            System.out.println("7. Exit");
            String choice = ConsoleUtil.readLine("Choose option");
            switch (choice) {
                case "1": manageStudents(); break;
                case "2": manageCourses(); break;
                case "3": manageEnrollment(); break;
                case "4": importExport(); break;
                case "5": backup(); break;
                case "6": reports(); break;
                case "7": break outer;
                default: System.out.println("Invalid");
            }
        }
        System.out.println("Goodbye!"); 
    }

    private static void seedSampleData() {
        Instructor i1 = new Instructor("I1","Dr. Alice","alice@uni.edu","CS");
        Instructor i2 = new Instructor("I2","Dr. Bob","bob@uni.edu","EE");
        DataStore.getInstance().getInstructors().put(i1.getId(), i1);
        DataStore.getInstance().getInstructors().put(i2.getId(), i2);

        Course c1 = new Course.Builder("CS101").title("Intro to CS").credits(4).instructor(i1).semester(Semester.FALL).department("CS").build();
        Course c2 = new Course.Builder("EE100").title("Intro to EE").credits(3).instructor(i2).semester(Semester.SPRING).department("EE").build();
        courseService.addCourse(c1); courseService.addCourse(c2);

        studentService.addStudent("S1","2025CS001","John Doe","john@example.com");
        studentService.addStudent("S2","2025CS002","Jane Roe","jane@example.com");
    }

    private static void manageStudents() {
        while (true) {
            System.out.println("-- Students --");
            System.out.println("1. Add  2. List  3. Profile  4. Back");
            String c = ConsoleUtil.readLine("choose");
            switch (c) {
                case "1":
                    String id = java.util.UUID.randomUUID().toString();
                    String reg = ConsoleUtil.readLine("regNo");
                    String name = ConsoleUtil.readLine("fullName");
                    String email = ConsoleUtil.readLine("email");
                    studentService.addStudent(id, reg, name, email);
                    System.out.println("Added.");
                    break;
                case "2":
                    studentService.listAll().forEach(s -> System.out.println(s));
                    break;
                case "3":
                    String sid = ConsoleUtil.readLine("student id");
                    studentService.findById(sid).ifPresentOrElse(s -> System.out.println(s.profile()), () -> System.out.println("Not found"));
                    break;
                case "4": return;
                default: System.out.println("Bad");
            }
        }
    }

    private static void manageCourses() {
        while (true) {
            System.out.println("-- Courses --");
            System.out.println("1.Add 2.List 3.Search 4.Back");
            String c = ConsoleUtil.readLine("choose");
            switch (c) {
                case "1":
                    String code = ConsoleUtil.readLine("code");
                    String title = ConsoleUtil.readLine("title");
                    int credits = Integer.parseInt(ConsoleUtil.readLine("credits"));
                    String dept = ConsoleUtil.readLine("department");
                    Course course = new Course.Builder(code).title(title).credits(credits).department(dept).build();
                    courseService.addCourse(course);
                    System.out.println("Course added");
                    break;
                case "2":
                    courseService.listAll().forEach(System.out::println);
                    break;
                case "3":
                    String q = ConsoleUtil.readLine("instr/dep/sem (prefix)");
                    courseService.filterByDepartment(q).forEach(System.out::println);
                    break;
                case "4": return;
                default: System.out.println("Bad");
            }
        }
    }

    private static void manageEnrollment() {
        while (true) {
            System.out.println("-- Enrollment/Grades --");
            System.out.println("1.Enroll 2.Unenroll 3.Record Grade 4.Print Transcript 5.Back");
            String c = ConsoleUtil.readLine("choose");
            try {
                switch (c) {
                    case "1":
                        enrollmentService.enrollStudent(ConsoleUtil.readLine("studentId"), ConsoleUtil.readLine("courseCode"));
                        System.out.println("Enrolled"); break;
                    case "2":
                        enrollmentService.unenrollStudent(ConsoleUtil.readLine("studentId"), ConsoleUtil.readLine("courseCode"));
                        System.out.println("Unenrolled"); break;
                    case "3":
                        String sid = ConsoleUtil.readLine("studentId");
                        String cc = ConsoleUtil.readLine("courseCode");
                        String grade = ConsoleUtil.readLine("grade (S/A/B/C/D/E/F)");
                        enrollmentService.recordGrade(sid, cc, Grade.valueOf(grade));
                        System.out.println("Recorded"); break;
                    case "4":
                        System.out.println(new TranscriptService().generateTranscript(ConsoleUtil.readLine("studentId")));
                        break;
                    case "5": return;
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private static void importExport() {
        while (true) {
            System.out.println("-- IO --");
            System.out.println("1.Import Students CSV 2.Export Students 3.Export Courses 4.Back");
            String c = ConsoleUtil.readLine("choose");
            try {
                switch (c) {
                    case "1":
                        String p = ConsoleUtil.readLine("path to students.csv");
                        ioService.importStudentsCsv(Paths.get(p));
                        System.out.println("Imported"); break;
                    case "2":
                        String out = ConsoleUtil.readLine("output path");
                        ioService.exportStudentsCsv(Paths.get(out));
                        System.out.println("Exported"); break;
                    case "3":
                        String out2 = ConsoleUtil.readLine("output path");
                        ioService.exportCoursesCsv(Paths.get(out2));
                        System.out.println("Exported"); break;
                    case "4": return;
                }
            } catch (Exception ex) { System.out.println("IO Error: " + ex.getMessage()); }
        }
    }

    private static void backup() {
        try {
            Path base = Paths.get("./data/backups");
            BackupService bs = new BackupService(base);
            Path target = bs.backupFolder();
            ioService.exportStudentsCsv(target.resolve("students.csv"));
            ioService.exportCoursesCsv(target.resolve("courses.csv"));
            long size = RecursionUtil.directorySize(target);
            System.out.println("Backup created at: " + target);
            System.out.println("Total size: " + size + " bytes");
        } catch (Exception ex) { System.out.println("Backup failed: " + ex.getMessage()); }
    }

    private static void reports() {
        System.out.println("-- Reports --");
        DataStore store = DataStore.getInstance();
        store.getStudents().values().stream().forEach(s -> {
            double gpa = new EnrollmentService().computeGPA(s.getId());
            System.out.println(s.getRegNo() + " -> GPA: " + String.format("%.2f", gpa));
        });
    }
}
