package com.agrismart.inventory.dto;

import com.agrismart.inventory.entity.TypeMouvement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/** Corps JSON pour créer un mouvement de stock. */
@Data
public class MouvementRequest {

    @NotNull(message = "produitId est obligatoire")
    private Long produitId;

    @NotNull(message = "type est obligatoire (ENTREE ou SORTIE)")
    private TypeMouvement type;

    @Positive(message = "La quantité doit être > 0")
    private double quantite;

    private String motif;
}
