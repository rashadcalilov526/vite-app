package com.example.msingress.service;

import com.example.msingress.dto.request.StudentCriteria;
import com.example.msingress.dto.request.StudentRequest;
import com.example.msingress.dto.response.PageableStudentResponse;
import com.example.msingress.dto.response.StudentResponse;
import com.example.msingress.mapper.StudentMapper;
import com.example.msingress.model.Course;
import com.example.msingress.model.Student;
import com.example.msingress.repository.CourseRepository;
import com.example.msingress.repository.StudentRepository;
import com.example.msingress.service.specification.StudentSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.msingress.mapper.StudentMapper.mapRequestToEntity;
import static org.springframework.data.domain.Sort.Direction.ASC;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    private final ModelMapper modelMapper;

    public void enrollStudentInCourseByNumber(String studentNumber, Long courseId) {
        Student student = fecthStudent(studentRepository.findByNumber(studentNumber));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));
        if (!student.getCourses().contains(course)) {
            student.getCourses().add(course);
            course.getStudents().add(student);
            studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("The student's course is available");
        }
    }

    public PageableStudentResponse getStudents(int page, int count, StudentCriteria studentCriteria) {
        var pageable = PageRequest.of(page, count, Sort.by(ASC, "name"));

        var pageableStudents =
                studentRepository.findAll(new StudentSpecification(studentCriteria), pageable);

        var students = pageableStudents.getContent()
                .stream()
                .map(StudentMapper::mapEntityToResponse)
                .toList();

        return PageableStudentResponse.builder()
                .student(students)
                .totalPages(pageableStudents.getTotalPages())
                .totalElements(pageableStudents.getTotalElements())
                .hasNextPage(pageableStudents.hasNext())
                .build();
    }
    public List<StudentResponse> getStudentsByCriteria(StudentCriteria studentCriteria) {
        var students = studentRepository.findAll(new StudentSpecification(studentCriteria));
        return students.stream()
                .map(StudentMapper::mapEntityToResponse)
                .collect(Collectors.toList());
    }
    public void removeCourseFromStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

        if (student.getCourses().contains(course)) {
            student.getCourses().remove(course);
            course.getStudents().remove(student);
        } else {
            throw new IllegalArgumentException("Student is not enrolled in the course.");
        }
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<String> getAllStudentsNames(){
        return studentRepository.getAllStudentNames();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(fecthStudent(student));
    }

    public void deleteStudentById(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student getStudentsByName(String name) {
        return fecthStudent(studentRepository.findByName(name));
    }

    public Student getStudentsByEmail(String email) {
        return fecthStudent(studentRepository.findByEmail(email));
    }

    public Student getStudentsByNumber(String number) {
        return fecthStudent(studentRepository.findByNumber(number));
    }
    private static Student fecthStudent(Student student) {
        return Optional.ofNullable(student)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public String getCourseNameById(Long id) {
        Student student=studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        return student.getName();
    }
    public Student findByNumber(String number) {
        return studentRepository.findByNumber(number);
    }
}
