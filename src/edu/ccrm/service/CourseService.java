
package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;

import java.util.*;
import java.util.stream.Collectors;

/** Course service with filter helpers */
public class CourseService {
    private final DataStore ds = DataStore.getInstance();

    public Course addCourse(Course c) {
        ds.getCourses().put(c.getCode(), c);
        return c;
    }

    public Optional<Course> findByCode(String code) { return Optional.ofNullable(ds.getCourses().get(code)); }

    public List<Course> listAll() { return new ArrayList<>(ds.getCourses().values()); }

    public List<Course> filterByInstructor(String instructorName) {
        return ds.getCourses().values().stream()
                .filter(c -> c.getInstructor() != null && c.getInstructor().getFullName().equalsIgnoreCase(instructorName))
                .collect(Collectors.toList());
    }

    public List<Course> filterByDepartment(String dept) {
        return ds.getCourses().values().stream()
                .filter(c -> c.getDepartment() != null && c.getDepartment().equalsIgnoreCase(dept))
                .collect(Collectors.toList());
    }

    public List<Course> filterBySemester(Semester s) {
        return ds.getCourses().values().stream()
                .filter(c -> c.getSemester() == s)
                .collect(Collectors.toList());
    }
}
