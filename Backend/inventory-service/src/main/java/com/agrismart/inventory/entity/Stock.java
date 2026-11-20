package com.agrismart.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero(message = "La quantité doit être >= 0")
    private double quantite;

    @PositiveOrZero(message = "La quantité minimale doit être >= 0")
    private double quantiteMinimale;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDerniereMiseAJour;

    @OneToOne
    @JoinColumn(name = "produit_id", nullable = false, unique = true)
    private Produit produit;
}
