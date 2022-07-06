package pl.arppl4_springdemo.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.arppl4_springdemo.model.Student;
import pl.arppl4_springdemo.service.StudentService;

import java.util.List;

// REST == HTTP == ZAPYTANIA == REQUESTS (mają 'metody')
// Wyróżniamy metody HTTP:
//  - GET     (pobierz)
//  - POST    (wstaw, edytuj)
//  - DELETE  (usuń)
//  - PUT     (wstaw, podmień)
//  - PATCH   (edytuj fragment [nie cały])
//  RESTFUL API
// http://localhost:8080
//  - protokół http
//  - host localhost
//  - port 8080
//  - CTX - pusty
// jdbc:mysql://localhost:3306/arppl4_spring_demo?serverTimezone=Europe/Warsaw&createDatabaseIfNotExist=true
//  - protokół jdbc:mysql
//  - host localhost
//  - port 3306
//  - CTX - arppl4_spring_demo
@Slf4j
@RequestMapping("/api/student")
@RestController()
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    @GetMapping
    public List<Student> studentList() {
        log.info("Wywołano metodę studentList");

        List<Student> studentList = studentService.findAll();

        return studentList;
    }

    // PathVariable - Zmienna podana w ścieżce
    // http://localhost:8080/api/student/5

    @GetMapping("/{identifier}")
    public Student findStudent(@PathVariable(name = "identifier") Long studentId) {
        log.info("Wywołano metodę findStudent: " + studentId);
        return studentService.findById(studentId);
    }

    // REST -> Representation State Transfer
    // Resource
    // PathVariable - Zmienna podana w ścieżce
    // http://localhost:8080/api/student/5

    @DeleteMapping("/{identifier}")
    public void deleteStudent(@PathVariable(name = "identifier") Long studentId) {
        log.info("Wywołano metodę deleteStudent: " + studentId);

        studentService.deleteById(studentId);
    }

//    // Request Param - parametr zapytania
//    // http://localhost:8080/api/student/find?studentId=5

    // Request Param - parametr zapytania
    // Select * from Student s where s.name LIKE %Gawel%
    // http://localhost:8080/api/student/findByName=Pawel
    @GetMapping("/findByName")
    public List<Student> findStudentByName(@RequestParam(name = "name") String searchedName) {
        log.info("Wywołano metodę findStudentByName: " + searchedName);
        return studentService.findAllByNameContaining(searchedName);
//            return studentRepository.findAllByNameContaining(searchedName);
    }

    @GetMapping("/find")
    public Student findStudentById(@RequestParam(name = "studentId") Long studentId) {
        log.info("Wywołano metodę findStudentById: " + studentId);
        return studentService.findById(studentId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(@RequestBody Student student) {
        log.info("Wywołano metodę createStudent: " + student);
        studentService.save(student);
    }
}

// RestController -> Zwraca DANE!
// Controller     -> Zwraca HTML - nie na dzisiaj
//
// [Controller] -> [ -> ] -> [Repository]