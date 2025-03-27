package com.starchenko.ParsingProject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {
    @JsonProperty("rank_tier")
    private int rankTier;
    @JsonProperty("leaderboard_rank")
    private int leaderboardRank;
    @JsonProperty("profile")
    private Profile profile;

    @Getter
    @Setter
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Profile {
        @JsonProperty("account_id")
        private int accountId;
        @JsonProperty("personaname")
        private String personName;
        @JsonProperty("name")
        private String realName;
        @JsonProperty("steamid")
        private String steamId;
        @JsonProperty("avatarfull")
        private String avatarFull;
        @JsonProperty("profileurl")
        private String profileUrl;
    }
}
