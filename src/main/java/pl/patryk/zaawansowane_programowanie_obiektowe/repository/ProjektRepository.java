package pl.patryk.zaawansowane_programowanie_obiektowe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Projekt;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Student;

import java.util.List;
import java.util.Set;

public interface ProjektRepository extends JpaRepository<Projekt, Integer> {

  //  @Query("SELECT p FROM Projekt p WHERE upper(p.nazwa) LIKE upper(:nazwa)")
    Page<Projekt> findByNazwaContainingIgnoreCase(String nazwa, Pageable pageable);
   // @Query("SELECT p FROM Projekt p WHERE upper(p.nazwa) LIKE upper(:nazwa)")
    List<Projekt> findByNazwaContainingIgnoreCase(String nazwa);

    Set<Projekt> findByProjektWlasciciel(Student student);
}
