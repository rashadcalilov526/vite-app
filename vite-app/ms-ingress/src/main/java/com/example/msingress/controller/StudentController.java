package com.example.msingress.controller;

import com.example.msingress.dto.RemoveCourseDto;
import com.example.msingress.dto.request.EnrollRequest;
import com.example.msingress.dto.request.StudentCriteria;
import com.example.msingress.dto.request.StudentRequest;
import com.example.msingress.dto.response.PageableStudentResponse;
import com.example.msingress.dto.response.StudentResponse;
import com.example.msingress.model.Student;
import com.example.msingress.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/name/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getStudentNameById(@PathVariable Long id) {
        return studentService.getCourseNameById(id);
    }
    @GetMapping("/all-names")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllStudentsNames() {
        return studentService.getAllStudentsNames();
    }
    @PostMapping("remove")
    public void removeCourseFromStudent(@RequestBody RemoveCourseDto removeCourseDto) {
        studentService.removeCourseFromStudent(removeCourseDto.getStudentId(), removeCourseDto.getCourseId());
    }
    @PostMapping("/enroll")
    public void enrollStudentInCourseByNumber(@RequestParam String number,@RequestParam Long courseId){
     studentService.enrollStudentInCourseByNumber(number, courseId);
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStudent(@RequestBody Student studentRequest) {
       studentService.saveStudent(studentRequest);
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestBody StudentRequest studentRequest) {
        Student existingStudent = studentService.getStudentsByNumber(studentRequest.getNumber());

        if (existingStudent == null) {
            throw new RuntimeException("student not found");
        }

        // Update the fields of the existing student with the data from the request
        existingStudent.setName(studentRequest.getName());
        existingStudent.setSurname(studentRequest.getSurname());
        existingStudent.setNumber(studentRequest.getNumber());
        existingStudent.setEmail(studentRequest.getEmail());

        return studentService.updateStudent(existingStudent);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
    }

    @GetMapping("/byName/{name}")
    public Student getStudentByName(@PathVariable String name) {
        return studentService.getStudentsByName(name);
    }

    @GetMapping("/byEmail/{email}")
    public Student getStudentByEmail(@PathVariable String email) {
        return studentService.getStudentsByEmail(email);
    }

    @GetMapping("/byNumber/{number}")
    public Student getStudentByNumber(@PathVariable String number) {
        return studentService.getStudentsByNumber(number);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Student searchAccount(@RequestParam String number) {
        // Call the accountService to search for accounts by number
        Student account = studentService.findByNumber(number);

        if (account != null) {
            // Create and return a response containing the account details
            Student response = new Student();
            response.setId(account.getId());
            response.setName(account.getName());
            response.setNumber(account.getNumber());
            response.setSurname(account.getSurname());
            response.setEmail(account.getEmail());

            return response;
        } else {
            // If no account is found, throw a NotFoundException
            throw new RuntimeException("Student not found");
        }
    }
}
