package com.starchenko.ParsingProject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeroDuration {
    @JsonProperty("duration_bin")
    private String durationBin;
    @JsonProperty("games_played")
    private int gamesPlayed;
    @JsonProperty("wins")
    private int wins;
}
