package pl.patryk.zaawansowane_programowanie_obiektowe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Projekt;

import java.util.Optional;

public interface ProjektService {

    Optional<Projekt> getProjekt(Integer projektId);
    Projekt setProjekt(Projekt projekt);
    void deleteProjekt(Integer projektId);
    Page<Projekt> getProjekty(Pageable pageable);
    Page<Projekt> searchByNazwa(String nazwa, Pageable pageable);


}
