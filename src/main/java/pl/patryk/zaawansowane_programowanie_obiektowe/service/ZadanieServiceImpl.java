package pl.patryk.zaawansowane_programowanie_obiektowe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Projekt;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Student;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Zadanie;
import pl.patryk.zaawansowane_programowanie_obiektowe.repository.ProjektRepository;
import pl.patryk.zaawansowane_programowanie_obiektowe.repository.StudentRepository;
import pl.patryk.zaawansowane_programowanie_obiektowe.repository.ZadanieRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ZadanieServiceImpl implements ZadanieService{

    StudentRepository studentRepository;
    ProjektRepository projektRepository;
    ZadanieRepository zadanieRepository;


    @Override
    public Optional<Zadanie> getZadanie(Integer zadanieId) {
        return zadanieRepository.findById(zadanieId);
    }

    @Override
    public Page<Zadanie> getZadania(Pageable pageable) {
        return zadanieRepository.findAll(pageable);
    }

    @Override
    public Zadanie setZadanie(Zadanie zadanie) {
        return zadanieRepository.save(zadanie);
    }

    @Override
    public void deleteZadanie(Integer zadanieId) {
zadanieRepository.deleteById(zadanieId);
    }

    @Override
    public Page<Zadanie> searchByNazwa(String name, Pageable pageable) {
        return zadanieRepository.findByNazwaContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<Zadanie> getZadaniaByProjektId(Integer projectId, Pageable pageable) {
        return zadanieRepository.findZadaniaProjektu(projectId, pageable);
    }

    @Override
    public Optional<Set<Zadanie>> getStudentZadania(Integer studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        return optionalStudent.map(student -> student.getProjekty().stream().map(Projekt::getZadania)
                .flatMap(Collection::stream).collect(Collectors.toSet()));
    }

    @Override
    public Optional<Set<Zadanie>> getWlascicielaZadania(Integer studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        return optionalStudent.map(student -> projektRepository.findByProjektWlasciciel(student).stream().map(project -> zadanieRepository.findByProjekt(project))
                .flatMap(Collection::stream).collect(Collectors.toSet()));
    }

    @Override
    public Projekt addZadanieToProjekt(Integer zadanieId, Integer projectId) {
        Optional<Projekt> optionalProject = projektRepository.findById(projectId);
        Optional<Zadanie> optionalTask = zadanieRepository.findById(zadanieId);
        if (optionalProject.isPresent() && optionalTask.isPresent()) {
            Projekt projektFound = optionalProject.get();
            projektFound.getZadania().add(optionalTask.get());
            return projektRepository.save(projektFound);
        }
        return null;
    }
}
