package com.agrismart.inventory.repository;

import com.agrismart.inventory.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    List<Produit> findByCategorieIgnoreCase(String categorie);
}
