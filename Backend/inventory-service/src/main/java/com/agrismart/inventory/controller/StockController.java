package com.agrismart.inventory.controller;

import com.agrismart.inventory.dto.StockRequest;
import com.agrismart.inventory.entity.Stock;
import com.agrismart.inventory.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    public List<Stock> getAll() {
        return stockService.findAll();
    }

    /** Stocks dont la quantité est sous le seuil minimal. */
    @GetMapping("/alertes")
    public List<Stock> getAlertes() {
        return stockService.findStocksEnAlerte();
    }

    @GetMapping("/{id}")
    public Stock getById(@PathVariable Long id) {
        return stockService.findById(id);
    }

    @GetMapping("/produit/{produitId}")
    public Stock getByProduit(@PathVariable Long produitId) {
        return stockService.findByProduit(produitId);
    }

    @PostMapping
    public ResponseEntity<Stock> create(@Valid @RequestBody StockRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stockService.create(request));
    }

    @PutMapping("/{id}")
    public Stock update(@PathVariable Long id, @Valid @RequestBody StockRequest request) {
        return stockService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        stockService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
