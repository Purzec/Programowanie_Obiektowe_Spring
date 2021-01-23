package pl.patryk.zaawansowane_programowanie_obiektowe.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Projekt;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Zadanie;

import java.util.List;
import java.util.Set;

public interface ZadanieRepository extends JpaRepository<Zadanie , Integer> {

    @Query("SELECT z FROM Zadanie z WHERE z.projekt.projektId = :projektId")
    Page<Zadanie> findZadaniaProjektu(@Param("projektId") Integer projektId, Pageable pageable);
    @Query("SELECT z FROM Zadanie z WHERE z.projekt.projektId = :projektId")
    List<Zadanie> findZadaniaProjektu(@Param("projektId") Integer projektId);


    Page<Zadanie> findByNazwaContainingIgnoreCase(String name, Pageable pageable);

    Set<Zadanie> findByProjekt(Projekt project);


}
