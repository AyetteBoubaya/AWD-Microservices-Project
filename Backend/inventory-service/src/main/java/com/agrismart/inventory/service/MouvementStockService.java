package com.agrismart.inventory.service;

import com.agrismart.inventory.dto.MouvementRequest;
import com.agrismart.inventory.entity.MouvementStock;
import com.agrismart.inventory.entity.Produit;
import com.agrismart.inventory.entity.Stock;
import com.agrismart.inventory.entity.TypeMouvement;
import com.agrismart.inventory.exception.BusinessException;
import com.agrismart.inventory.exception.ResourceNotFoundException;
import com.agrismart.inventory.repository.MouvementStockRepository;
import com.agrismart.inventory.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MouvementStockService {

    private final MouvementStockRepository mouvementRepository;
    private final StockRepository stockRepository;
    private final ProduitService produitService;

    public List<MouvementStock> findAll() {
        return mouvementRepository.findAll();
    }

    public MouvementStock findById(Long id) {
        return mouvementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mouvement introuvable avec id = " + id));
    }

    public List<MouvementStock> findByProduit(Long produitId) {
        return mouvementRepository.findByProduitIdOrderByDateMouvementDesc(produitId);
    }

    /**
     * Règle métier :
     *  - ENTREE : ajoute la quantité au stock (crée le stock s'il n'existe pas encore)
     *  - SORTIE : retire la quantité, refusé si le stock est insuffisant
     *  - met à jour dateDerniereMiseAJour
     */
    @Transactional
    public MouvementStock create(MouvementRequest request) {
        Produit produit = produitService.findById(request.getProduitId());
        Date maintenant = new Date();

        Stock stock = stockRepository.findByProduitId(produit.getId()).orElse(null);

        if (request.getType() == TypeMouvement.ENTREE) {
            if (stock == null) {
                stock = Stock.builder()
                        .produit(produit)
                        .quantite(0)
                        .quantiteMinimale(0)
                        .build();
            }
            stock.setQuantite(stock.getQuantite() + request.getQuantite());
        } else { // SORTIE
            double disponible = (stock == null) ? 0 : stock.getQuantite();
            if (stock == null || disponible < request.getQuantite()) {
                throw new BusinessException("Stock insuffisant pour le produit '" + produit.getNom()
                        + "' : disponible = " + disponible + ", demandé = " + request.getQuantite());
            }
            stock.setQuantite(disponible - request.getQuantite());
        }

        stock.setDateDerniereMiseAJour(maintenant);
        stockRepository.save(stock);

        MouvementStock mouvement = MouvementStock.builder()
                .produit(produit)
                .type(request.getType())
                .quantite(request.getQuantite())
                .motif(request.getMotif())
                .dateMouvement(maintenant)
                .build();
        return mouvementRepository.save(mouvement);
    }

    /** Supprime uniquement l'historique (ne modifie pas le stock). */
    @Transactional
    public void delete(Long id) {
        MouvementStock mouvement = findById(id);
        mouvementRepository.delete(mouvement);
    }
}
