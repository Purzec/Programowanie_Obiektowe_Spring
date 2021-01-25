package pl.patryk.zaawansowane_programowanie_obiektowe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Student;

import java.util.Optional;
import java.util.Set;

public interface StudentService {

    Optional<Student> getStudent(Integer studentId);

   // Optional<Student> getStudent(String index);

    Student setStudent(Student student);

    void deleteStudent(Integer studentId);

    Page<Student> getStudents(Pageable pageable);

    Page<Student> searchByNazwisko(String surname, Pageable pageable);

    Optional<Set<Student>> getStudentsByProjectId(Integer projectId);


}
