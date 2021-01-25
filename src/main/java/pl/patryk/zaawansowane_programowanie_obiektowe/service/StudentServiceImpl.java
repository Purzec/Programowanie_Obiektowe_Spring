package pl.patryk.zaawansowane_programowanie_obiektowe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Projekt;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Student;
import pl.patryk.zaawansowane_programowanie_obiektowe.repository.ProjektRepository;
import pl.patryk.zaawansowane_programowanie_obiektowe.repository.StudentRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService{

StudentRepository studentRepository;
ProjektRepository projektRepository;

@Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ProjektRepository projektRepository) {
        this.studentRepository = studentRepository;
        this.projektRepository = projektRepository;
    }


    @Override
    public Optional<Student> getStudent(Integer studentId) {
        return studentRepository.findById(studentId);
    }

  /*  @Override
    public Optional<Student> getStudent(String index) {
        return studentRepository.findByNrIndeksu(index);
    }*/

    @Override
    public Student setStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Integer studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public Page<Student> getStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> searchByNazwisko(String nazwisko, Pageable pageable) {
        return studentRepository.findByNazwiskoStartsWithIgnoreCase(nazwisko,pageable);
    }

    @Override
    public Optional<Set<Student>> getStudentsByProjectId(Integer projectId) {
        Optional<Projekt> optionalProject= projektRepository.findById(projectId);
        if(optionalProject.isPresent()){
            return Optional.of(optionalProject.map(Projekt::getStudenci).get());
        }else {
            return Optional.empty();
        }
    }
}
