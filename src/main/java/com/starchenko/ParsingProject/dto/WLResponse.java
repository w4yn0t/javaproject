package com.starchenko.ParsingProject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WLResponse {
    @JsonProperty("win")
    private int win;
    @JsonProperty("lose")
    private int lose;
    @JsonProperty("winrate")
    public String getWinrate() {
        return (win + lose) == 0 ? "0%"
                : String.format("%.2f%%", ((double) win / (win + lose)) * 100);
    }
}
