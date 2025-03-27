package com.starchenko.ParsingProject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hero {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("localized_name")
    private String localizedName;
    @JsonProperty("primary_attr")
    private PrimaryAttribute primaryAttr;
    @JsonProperty("attack_type")
    private AttackType attackType;
    @JsonProperty("roles")
    private List<String> roles;
    @JsonProperty("legs")
    private int legs;
}
