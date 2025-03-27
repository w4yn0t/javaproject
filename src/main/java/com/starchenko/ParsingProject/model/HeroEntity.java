package com.starchenko.ParsingProject.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "heroes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeroEntity {
    @Id
    private int id;
    private String name;
    @Column(name = "localized_name")
    private String localizedName;
    @Column(name = "primary_attr")
    private String primaryAttr;
    @Column(name = "attack_type")
    private String attackType;
    @Column(length = 512)
    private String roles;
}
