package pl.patryk.zaawansowane_programowanie_obiektowe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Projekt;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Student;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Zadanie;
import pl.patryk.zaawansowane_programowanie_obiektowe.service.StudentService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private StudentService studentService;

@Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/studenci/{studentId}")
    ResponseEntity<Student> getStudent(@PathVariable Integer studentId){
    return ResponseEntity.of(studentService.getStudent(studentId));
    }



    @PostMapping(path = "/studenci")
    ResponseEntity<Void> createStudent(@Valid @RequestBody Student student){
    Student createdStudent = studentService.setStudent(student);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{studentId}").buildAndExpand(createdStudent.getStudentId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/studenci/{studentId}")
    public ResponseEntity<Void> updateStudent(@Valid @RequestBody Student student,
                                              @PathVariable Integer studentId) {

        return studentService.getStudent(studentId)
                .map(p -> {
                    studentService.setStudent(student);
                    return new ResponseEntity<Void>(HttpStatus.OK); // 200 (można też zwracać 204 - No content)
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 - Not found
    }
    @DeleteMapping("/studenci/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer studentId) {
        return studentService.getStudent(studentId).map(p -> {
            studentService.deleteStudent(studentId);
            return new ResponseEntity<Void>(HttpStatus.OK); // 200
        }).orElseGet(() -> ResponseEntity.notFound().build()); // 404 - Not found
    }

    @GetMapping(value = "/studenci")
    Page<Student> getStudenci(Pageable pageable) { // @RequestHeader HttpHeaders headers – jeżeli potrzebny
        return studentService.getStudents(pageable); // byłby nagłówek, wystarczy dodać drugą zmienną z adnotacją
    }

    @GetMapping(value = "/studenci", params="nazwa")
    Page<Student> getByNazwisko(@RequestParam String nazwa, Pageable pageable) {
        return studentService.searchByNazwisko(nazwa, pageable);
    }

}
