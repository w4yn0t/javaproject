package com.starchenko.ParsingProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "heroes_durations_nested")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeroDurationsNestedEntity {
    @Id
    @Column(name = "hero_name")
    private String heroName;
    @OneToMany(mappedBy = "hero", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HeroDurationsEntity> heroDurationsEntities;
}
