package com.starchenko.ParsingProject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.starchenko.ParsingProject.dto.Player;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity {
    @Id
    @Column(name = "account_id")
    private int accountId;
    @Column(name = "persona_name")
    private String personName;
    @Column(name = "name")
    private String realName;
    @Column(name = "steam_id")
    private String steamId;
    @Column(name = "avatar_full")
    private String avatarFull;
    @Column(name = "profile_url")
    private String profileUrl;
    @Column(name = "rank_tier")
    private int rankTier;
    @Column(name = "leaderboard_rank")
    private int leaderboardRank;

    // last 100 games
    private int win;
    private int lose;
    private String winrate;
}
