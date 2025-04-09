package com.esprit.gestionrecette.controller;

import com.esprit.gestionrecette.entites.Recette;
import com.esprit.gestionrecette.service.PdfService;
import com.esprit.gestionrecette.service.RecetteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recettes")
public class RecetteController {

    @Autowired
    private RecetteService recetteService;

    @Autowired
    private PdfService pdfService;

    @PostMapping
    public Recette createRecette(@RequestBody Recette recette) {
        return recetteService.createRecette(recette);
    }

    @GetMapping
    public List<Recette> getAllRecettes() {
        return recetteService.getAllRecettes();
    }

    @GetMapping("details/{id}")
    public Optional<Recette> getRecetteById(@PathVariable Long id) {
        return recetteService.getRecetteById(id);
    }

    @PutMapping("update/{id}")
    public Recette updateRecette(@PathVariable Long id, @RequestBody Recette recetteDetails) {
        return recetteService.updateRecette(id, recetteDetails);
    }


    @DeleteMapping("delete/{id}")
    public void deleteRecette(@PathVariable Long id) {
        recetteService.deleteRecette(id);
    }

    @GetMapping("/search")
    public List<Recette> searchByTitre(@RequestParam String keyword) {
        return recetteService.searchRecetteByTitreLike(keyword);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadRecettePdf(@PathVariable Long id) throws IOException {
        Optional<Recette> recetteOptional = recetteService.getRecetteById(id);
        if (recetteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        byte[] pdfBytes = pdfService.generatePdfForRecette(recetteOptional.get());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=recette_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }


}


