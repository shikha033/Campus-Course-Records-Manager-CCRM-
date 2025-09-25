
package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Student;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Student service demonstrating Streams, Optional and collection operations
 */
public class StudentService {
    private final DataStore ds = DataStore.getInstance();

    public Student addStudent(String id, String regNo, String name, String email) {
        Student s = new Student(id, regNo, name, email);
        ds.getStudents().put(id, s);
        return s;
    }

    public Optional<Student> findById(String id) { return Optional.ofNullable(ds.getStudents().get(id)); }

    public List<Student> listAll() { return new ArrayList<>(ds.getStudents().values()); }

    public List<Student> searchByName(String q) {
        return ds.getStudents().values().stream()
                .filter(s -> s.getFullName().toLowerCase().contains(q.toLowerCase()))
                .collect(Collectors.toList());
    }
}
