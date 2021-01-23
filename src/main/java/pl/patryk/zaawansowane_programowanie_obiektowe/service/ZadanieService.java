package pl.patryk.zaawansowane_programowanie_obiektowe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Projekt;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Zadanie;

import java.util.Optional;
import java.util.Set;

public interface ZadanieService {

    Optional<Zadanie> getZadanie(Integer zadanieId);

    Page<Zadanie> getZadania(Pageable pageable);

    Zadanie setZadanie(Zadanie zadanie);

    void deleteZadanie(Integer zadanieId);

    Page<Zadanie> searchByNazwa(String name, Pageable pageable);

    Page<Zadanie> getZadaniaByProjektId(Integer projectId, Pageable pageable);

    Optional<Set<Zadanie>> getStudentZadania(Integer studentId);

    Optional<Set<Zadanie>> getWlascicielaZadania(Integer studentId);

    Projekt addZadanieToProjekt(Integer zadanieId, Integer projectId);

}
