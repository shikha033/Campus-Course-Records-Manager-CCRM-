
package edu.ccrm.io;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Simple CSV import/export using NIO.2 + Streams
 */
public class ImportExportService {
    private final DataStore ds = DataStore.getInstance();

    public void importStudentsCsv(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.filter(l -> !l.trim().startsWith("#") && !l.trim().isEmpty())
                 .skip(1)
                 .forEach(line -> {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        String id = parts[0].trim();
                        String regNo = parts[1].trim();
                        String name = parts[2].trim();
                        String email = parts[3].trim();
                        ds.getStudents().put(id, new Student(id, regNo, name, email));
                    }
                 });
        }
    }

    public void exportStudentsCsv(Path path) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("id,regNo,fullName,email");
        ds.getStudents().values().forEach(s -> lines.add(String.join(",",
                s.getId(), s.getRegNo(), s.getFullName(), s.getEmail())));
        Files.createDirectories(path.getParent());
        Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void exportCoursesCsv(Path path) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("code,title,credits,department,semester,instructor");
        ds.getCourses().values().forEach(c -> lines.add(String.join(",",
                c.getCode(), c.getTitle(), String.valueOf(c.getCredits()), c.getDepartment()==null?"":c.getDepartment(),
                c.getSemester()==null?"":c.getSemester().name(), c.getInstructor()==null?"":c.getInstructor().getFullName())));
        Files.createDirectories(path.getParent());
        Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
