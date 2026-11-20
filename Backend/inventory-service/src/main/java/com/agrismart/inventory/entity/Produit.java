package com.agrismart.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String categorie;

    private String unite;

    // Produit 1 --- 0..1 Stock (côté inverse, ignoré en JSON pour éviter la récursion)
    @JsonIgnore
    @OneToOne(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Stock stock;

    // Produit 1 --- 0..* MouvementStock
    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MouvementStock> mouvements = new ArrayList<>();
}
