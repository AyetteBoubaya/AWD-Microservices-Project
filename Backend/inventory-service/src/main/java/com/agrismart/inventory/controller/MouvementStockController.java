package com.agrismart.inventory.controller;

import com.agrismart.inventory.dto.MouvementRequest;
import com.agrismart.inventory.entity.MouvementStock;
import com.agrismart.inventory.service.MouvementStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mouvements")
@RequiredArgsConstructor
public class MouvementStockController {

    private final MouvementStockService mouvementService;

    @GetMapping
    public List<MouvementStock> getAll() {
        return mouvementService.findAll();
    }

    @GetMapping("/{id}")
    public MouvementStock getById(@PathVariable Long id) {
        return mouvementService.findById(id);
    }

    @GetMapping("/produit/{produitId}")
    public List<MouvementStock> getByProduit(@PathVariable Long produitId) {
        return mouvementService.findByProduit(produitId);
    }

    @PostMapping
    public ResponseEntity<MouvementStock> create(@Valid @RequestBody MouvementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mouvementService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mouvementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
