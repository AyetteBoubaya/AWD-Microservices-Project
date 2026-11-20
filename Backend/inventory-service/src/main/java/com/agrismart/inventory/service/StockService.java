package com.agrismart.inventory.service;

import com.agrismart.inventory.dto.StockRequest;
import com.agrismart.inventory.entity.Produit;
import com.agrismart.inventory.entity.Stock;
import com.agrismart.inventory.exception.BusinessException;
import com.agrismart.inventory.exception.ResourceNotFoundException;
import com.agrismart.inventory.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final ProduitService produitService;

    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public Stock findById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock introuvable avec id = " + id));
    }

    public Stock findByProduit(Long produitId) {
        return stockRepository.findByProduitId(produitId)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun stock pour le produit id = " + produitId));
    }

    /** Stocks en dessous de la quantité minimale. */
    public List<Stock> findStocksEnAlerte() {
        return stockRepository.findStocksEnAlerte();
    }

    @Transactional
    public Stock create(StockRequest request) {
        Produit produit = produitService.findById(request.getProduitId());
        if (stockRepository.existsByProduitId(produit.getId())) {
            throw new BusinessException("Un stock existe déjà pour le produit id = " + produit.getId());
        }
        Stock stock = Stock.builder()
                .produit(produit)
                .quantite(request.getQuantite())
                .quantiteMinimale(request.getQuantiteMinimale())
                .dateDerniereMiseAJour(new Date())
                .build();
        return stockRepository.save(stock);
    }

    @Transactional
    public Stock update(Long id, StockRequest request) {
        Stock stock = findById(id);
        stock.setQuantite(request.getQuantite());
        stock.setQuantiteMinimale(request.getQuantiteMinimale());
        stock.setDateDerniereMiseAJour(new Date());
        return stockRepository.save(stock);
    }

    @Transactional
    public void delete(Long id) {
        Stock stock = findById(id);
        stock.getProduit().setStock(null);
        stockRepository.delete(stock);
    }
}
