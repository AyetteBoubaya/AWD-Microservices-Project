package com.agrismart.inventory.repository;

import com.agrismart.inventory.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProduitId(Long produitId);

    boolean existsByProduitId(Long produitId);

    /** Stocks dont la quantité est inférieure à la quantité minimale (alerte). */
    @Query("SELECT s FROM Stock s WHERE s.quantite < s.quantiteMinimale")
    List<Stock> findStocksEnAlerte();
}
