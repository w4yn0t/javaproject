package com.starchenko.ParsingProject.service;

import com.starchenko.ParsingProject.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class OpenDotaAPIService {
    static String API_URL = "https://api.opendota.com/api";
    static String API_URL_HEROES = API_URL + "/heroes";
    static String API_URL_HEROES_DURATIONS = API_URL_HEROES + "/{hero_id}/durations";
    static String API_URL_PLAYERS = API_URL + "/players/{accountId}";
    static String API_URL_PLAYERS_WL = API_URL_PLAYERS + "/wl";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Hero> getHeroes() {
        return asList(restTemplate.getForObject(API_URL_HEROES, Hero[].class));
    }

    public Hero getHeroByName(String heroName) {
        return getHeroes()
                .stream()
                .filter(hero -> hero.getLocalizedName().equalsIgnoreCase(heroName))
                .findFirst()
                .orElse(null);
    }

    public List<HeroDuration> getHeroWinrateForDurations(String heroName) {
        return asList(restTemplate.getForObject(
                API_URL_HEROES_DURATIONS,
                HeroDuration[].class,
                getHeroByName(heroName).getId()
        ));
    }

    public Player getPlayerStatsById(int accountId) {
        return restTemplate.getForObject(API_URL_PLAYERS, Player.class, accountId);
    }

    public WLResponse getWLForGames(int accountId, int limit) {
        return restTemplate.getForObject(API_URL_PLAYERS_WL, WLResponse.class, accountId, limit);
    }
}
