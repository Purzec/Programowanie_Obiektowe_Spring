package pl.patryk.zaawansowane_programowanie_obiektowe.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Student;
import pl.patryk.zaawansowane_programowanie_obiektowe.model.Zadanie;
import pl.patryk.zaawansowane_programowanie_obiektowe.service.ZadanieService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class ZadanieRestController {

    ZadanieService zadanieService;

    @Autowired
    public ZadanieRestController(ZadanieService zadanieService) {
        this.zadanieService = zadanieService;
    }

    @PostMapping(path = "/zadanie")
    ResponseEntity<Void> createZadanie(@Valid @RequestBody Zadanie zadanie){
        Zadanie createdZadanie = zadanieService.setZadanie(zadanie);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{zadanieId}").buildAndExpand(createdZadanie.getZadanieId()).toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping("/zadanie/{zadanieId}")
    ResponseEntity<Zadanie> getZadanie(@PathVariable Integer zadanieId){
        return ResponseEntity.of(zadanieService.getZadanie(zadanieId));
    }

    @PutMapping("/zadanie/{zadanieId}")
    public ResponseEntity<Void> updateZadanie(@Valid @RequestBody Zadanie zadanie,
                                              @PathVariable Integer zadanieId) {

        return zadanieService.getZadanie(zadanieId)
                .map(p -> {
                    zadanieService.setZadanie(zadanie);
                    return new ResponseEntity<Void>(HttpStatus.OK); // 200 (można też zwracać 204 - No content)
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 - Not found
    }
    @DeleteMapping("/zadanie/{zadanieId}")
    public ResponseEntity<Void> deleteZadanie(@PathVariable Integer zadanieId) {
        return zadanieService.getZadanie(zadanieId).map(p -> {
            zadanieService.deleteZadanie(zadanieId);
            return new ResponseEntity<Void>(HttpStatus.OK); // 200
        }).orElseGet(() -> ResponseEntity.notFound().build()); // 404 - Not found
    }

    @GetMapping(value = "/zadanie")
    Page<Zadanie> getZadania(Pageable pageable) { // @RequestHeader HttpHeaders headers – jeżeli potrzebny
        return zadanieService.getZadania(pageable); // byłby nagłówek, wystarczy dodać drugą zmienną z adnotacją
    }

    @GetMapping(value = "/zadanie", params="nazwa")
    Page<Zadanie> getByNazwa(@RequestParam String nazwa, Pageable pageable) {
        return zadanieService.searchByNazwa(nazwa, pageable);
    }
}
