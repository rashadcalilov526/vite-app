package com.example.msingress.service;

import com.example.msingress.dto.request.CourseCriteria;
import com.example.msingress.dto.request.CourseRequest;
import com.example.msingress.dto.response.CourseResponse;
import com.example.msingress.dto.response.PageableCourseResponse;
import com.example.msingress.mapper.CourseMapper;
import com.example.msingress.model.Course;
import com.example.msingress.model.Student;
import com.example.msingress.repository.CourseRepository;
import com.example.msingress.repository.StudentRepository;
import com.example.msingress.service.specification.CourseSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.msingress.mapper.CourseMapper.mapRequestToEntity;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    public List<CourseResponse> getActiveCourses() {
        List<Course> activeCourses = courseRepository.findByIsActive();
        return activeCourses.stream()
                .map(course -> CourseResponse.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .isActive(course.getIsActive())
                        .build())
                .collect(Collectors.toList());
    }
    public void removeStudentFromCourse(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            Student studentToRemove = course.getStudents().stream()
                    .filter(student -> student.getId().equals(studentId))
                    .findFirst()
                    .orElse(null);

            if (studentToRemove != null) {
                course.removeStudent(studentToRemove);
                courseRepository.save(course);
            }
        }
    }

    public List<CourseResponse> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public CourseResponse mapEntityToResponse(Course entity) {
        return new CourseResponse(
                entity.getId(),
                entity.getName(),
                entity.getIsActive(),
                studentRepository.findByCourseId(entity.getId())
        );
    }

    public String getCourseNameById(Long id){
       Course course= courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
       return course.getName();
    }


    public Course updateCourse(Course course) {
        return courseRepository.save(fecthCourse(course));
    }

    public Course saveCourse(CourseRequest request) {
        Course course=fecthCourse(mapRequestToEntity(request));
        Course existingCourse = courseRepository.findByName(course.getName());

        if (existingCourse != null) {
            throw new RuntimeException("A course with the same name already exists.");
        }

        return courseRepository.save(course);
    }

    public void deleteCourseById(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course getCoursesByName(String name) {
        return (courseRepository.findByName(name));
    }

    public boolean isCourseNameExist(String name) {
        return courseRepository.existsByName(name);
    }
    public void enableCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        course.setIsActive(true);
        courseRepository.save(course);
    }
    public void disableCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        course.setIsActive(false);
        courseRepository.save(course);
    }

    public List<String> getAllCourseNames() {
        List<Course> courses = courseRepository.findAll();
        List<String> courseNames = new ArrayList<>();

        for (Course course : courses) {
            courseNames.add(course.getName());
        }

        return courseNames;
    }

    private static Course fecthCourse(Course course) {
        return Optional.ofNullable(course)
                .orElseThrow(() -> new RuntimeException("Course not found!!!"));
    }

    public Course findByName(String name) {
        return courseRepository.findByName(name);
    }

    public List<Student> getStudentsByCourseId(Long courseId) {
        // First, check if the course exists in the repository
        Course course = courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("course not found"));


        // Assuming studentRepository has a method to fetch students by courseId
        return studentRepository.findByCourseId(courseId);
    }
}
