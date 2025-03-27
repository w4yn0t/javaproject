package com.starchenko.ParsingProject.controller;

import com.starchenko.ParsingProject.model.HeroDurationsEntity;
import com.starchenko.ParsingProject.model.HeroEntity;
import com.starchenko.ParsingProject.model.PlayerEntity;
import com.starchenko.ParsingProject.service.HeroDurationsService;
import com.starchenko.ParsingProject.service.HeroService;
import com.starchenko.ParsingProject.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private HeroService heroService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private HeroDurationsService heroDurationsService;

    @GetMapping("/heroes")
    public String viewHeroes(Model model) {
        List<HeroEntity> heroes = heroService.getAllHeroes();
        model.addAttribute("heroes", heroes);
        return "heroes";
    }

    @GetMapping("/players")
    public String viewPlayers(Model model) {
        List<PlayerEntity> players = playerService.getAllPlayers();
        model.addAttribute("players", players);
        return "players";
    }

    @GetMapping("/heroDurations")
    public String viewHeroDurations(Model model) {
        model.addAttribute("heroDurations");
        return "heroDurations";
    }
}
