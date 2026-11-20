package com.agrismart.inventory.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

/** Corps JSON pour créer / modifier un stock. */
@Data
public class StockRequest {

    @NotNull(message = "produitId est obligatoire")
    private Long produitId;

    @PositiveOrZero(message = "La quantité doit être >= 0")
    private double quantite;

    @PositiveOrZero(message = "La quantité minimale doit être >= 0")
    private double quantiteMinimale;
}
