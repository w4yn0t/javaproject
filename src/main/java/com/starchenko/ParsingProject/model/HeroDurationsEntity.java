package com.starchenko.ParsingProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "heroes_durations")
@Getter
@Setter
@NoArgsConstructor
public class HeroDurationsEntity {
    @Id
    @Column(name = "duration_bin")
    private String durationBin;
    @Column(name = "games_played")
    private int gamesPlayed;
    private int wins;
    private String winrate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hero_name", referencedColumnName = "hero_name")
    private HeroDurationsNestedEntity hero;

    public HeroDurationsEntity(String durationBin, int gamesPlayed, int wins) {
        this.durationBin = durationBin;
        this.gamesPlayed = gamesPlayed;
        this.wins = wins;
        this.winrate = calculateWinrate();
    }
    private String calculateWinrate() {
        return String.format("%.2f%%",
                gamesPlayed > 0
                        ? (double) wins / gamesPlayed * 100
                        : 0
        );
    }
}
