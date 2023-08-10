package com.example.msingress.controller;

import com.example.msingress.dto.StatusDto;
import com.example.msingress.dto.request.CourseCriteria;
import com.example.msingress.dto.request.CourseRequest;
import com.example.msingress.dto.request.StudentRequest;
import com.example.msingress.dto.response.CourseResponse;
import com.example.msingress.dto.response.CourseStudentsResponse;
import com.example.msingress.dto.response.PageableCourseResponse;
import com.example.msingress.model.Course;
import com.example.msingress.model.Student;
import com.example.msingress.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    @GetMapping("/{courseId}/students")
    @ResponseStatus(HttpStatus.OK)
    public List<Student> getCourseStudents(@PathVariable(required = false) Long courseId) {
        return courseService.getStudentsByCourseId(courseId);
    }
    @PostMapping("/{courseId}/enable")
    @ResponseStatus(HttpStatus.OK)
    public void enableCourse(@PathVariable Long courseId) {
        courseService.enableCourse(courseId);
    }

    @PostMapping("/{courseId}/disable")
    @ResponseStatus(HttpStatus.OK)
    public void disableCourse(@PathVariable Long courseId) {
        courseService.disableCourse(courseId);
    }
    @GetMapping("/name/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getCourseNameById(@PathVariable Long id) {
        return courseService.getCourseNameById(id);
    }

    @GetMapping("/exists/{name}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isCourseNameExist(@PathVariable String name) {
        return courseService.isCourseNameExist(name);
    }
    @GetMapping("/all")
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllCourses();
    }



    @PostMapping("/management")
    public void removeStudentFromCourse(@RequestParam Long courseId,
                                        @RequestParam Long studentId) {
        courseService.removeStudentFromCourse(courseId, studentId);
    }
    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getActiveCourses() {
        return courseService.getActiveCourses();
    }
    @GetMapping("/get/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PutMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCourse(@RequestBody CourseRequest course) {
        courseService.saveCourse(course);
    }

    @PutMapping("/update")
    public Course updateCourse(@RequestBody Course course) {
        Course existingCourse = courseService.getCourseById(course.getId());

        if (existingCourse == null) {
            throw new RuntimeException("course not found");
        }

        // Update the fields of the existing student with the data from the request
        existingCourse.setName(course.getName());

        return courseService.updateCourse(existingCourse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourseById(id);
    }

    @GetMapping("/names")
    public List<String> getAllCourseNames() {
        return courseService.getAllCourseNames();
    }

    @GetMapping("/byName/{name}")
    public Course getCourseByName(@PathVariable String name) {
        return courseService.getCoursesByName(name);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Course searchAccount(@RequestParam String name) {
        // Call the accountService to search for accounts by number
        Course account = courseService.findByName(name);

        if (account != null) {
            // Create and return a response containing the account details
            Course response = new Course();
            response.setId(account.getId());
            response.setName(account.getName());
            response.setIsActive(false);

            return response;
        } else {
            // If no account is found, throw a NotFoundException
            throw new RuntimeException("Course not found");
        }
    }


}
