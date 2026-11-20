package com.agrismart.inventory.service;

import com.agrismart.inventory.entity.Produit;
import com.agrismart.inventory.exception.ResourceNotFoundException;
import com.agrismart.inventory.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitRepository produitRepository;

    public List<Produit> findAll() {
        return produitRepository.findAll();
    }

    public Produit findById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable avec id = " + id));
    }

    public List<Produit> findByCategorie(String categorie) {
        return produitRepository.findByCategorieIgnoreCase(categorie);
    }

    @Transactional
    public Produit create(Produit produit) {
        produit.setId(null);
        return produitRepository.save(produit);
    }

    @Transactional
    public Produit update(Long id, Produit data) {
        Produit produit = findById(id);
        produit.setNom(data.getNom());
        produit.setCategorie(data.getCategorie());
        produit.setUnite(data.getUnite());
        return produitRepository.save(produit);
    }

    @Transactional
    public void delete(Long id) {
        Produit produit = findById(id);
        produitRepository.delete(produit);
    }
}
