package com.agrismart.inventory.controller;

import com.agrismart.inventory.entity.Produit;
import com.agrismart.inventory.service.ProduitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping
    public List<Produit> getAll(@RequestParam(required = false) String categorie) {
        return (categorie == null) ? produitService.findAll() : produitService.findByCategorie(categorie);
    }

    @GetMapping("/{id}")
    public Produit getById(@PathVariable Long id) {
        return produitService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Produit> create(@Valid @RequestBody Produit produit) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produitService.create(produit));
    }

    @PutMapping("/{id}")
    public Produit update(@PathVariable Long id, @Valid @RequestBody Produit produit) {
        return produitService.update(id, produit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
