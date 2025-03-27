package com.starchenko.ParsingProject.controller;

import com.starchenko.ParsingProject.dto.*;
import com.starchenko.ParsingProject.model.HeroDurationsEntity;
import com.starchenko.ParsingProject.model.HeroDurationsNestedEntity;
import com.starchenko.ParsingProject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dota")
public class ApiController {
    @Autowired
    private OpenDotaAPIService openDotaAPIService;
    @Autowired
    private ExcelExportService excelExportService;
    @Autowired
    private HeroService heroService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private HeroDurationsService heroDurationsService;

    @GetMapping("/heroes")
    public ResponseEntity<List<Hero>> getHeroes() {
        return ResponseEntity.ok(openDotaAPIService.getHeroes());
    }

    @GetMapping("/heroes/{heroName}")
    public ResponseEntity<Hero> getHeroByName(@PathVariable String heroName) {
        return ResponseEntity.ok(openDotaAPIService.getHeroByName(heroName));
    }

    @GetMapping("/heroes/{heroName}/durations")
    public ResponseEntity<List<HeroDuration>> getHeroWinrateForDurations(@PathVariable String heroName) {
        return ResponseEntity.ok(openDotaAPIService.getHeroWinrateForDurations(heroName));
    }

    @GetMapping("/players/{accountId}")
    public ResponseEntity<Player> getPlayerStatsById(@PathVariable int accountId) {
        return ResponseEntity.ok(openDotaAPIService.getPlayerStatsById(accountId));
    }

    @GetMapping("/dota/players/{accountId}/wl")
    public ResponseEntity<WLResponse> checkWLForGames(@PathVariable int accountId,
                                                      @RequestParam(required = false) int limit) {
        return ResponseEntity.ok(openDotaAPIService.getWLForGames(accountId, limit));
    }

    @GetMapping("/export/heroes")
    public ResponseEntity<byte[]> exportHeroes() throws IOException {
        byte[] excelFile = excelExportService.exportHeroesToExcel();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("heroes.xlsx").build());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
    }

    @GetMapping("/export/players")
    public ResponseEntity<byte[]> exportPlayers() throws IOException {
        byte[] excelFile = excelExportService.exportPlayersToExcel();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("players.xlsx").build());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
    }

    @PostMapping("/load/heroes")
    public ResponseEntity<String> loadHeroes() {
        try {
            heroService.loadAndSaveHeroes();
            return ResponseEntity.ok("Герои успешно загружены");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при загрузке героев: " + e.getMessage());
        }
    }

    @PostMapping("/load/players")
    public ResponseEntity<String> loadPlayer(@RequestParam("accountId") int accountId) {
        try {
            playerService.loadAndSavePlayerById(accountId);
            return ResponseEntity.ok("Игрок с accountId " + accountId + " успешно загружен");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при загрузке игрока: " + e.getMessage());
        }
    }

    @PostMapping("/load/heroDurations")
    public ResponseEntity<List<HeroDurationsEntity>> loadHeroDurations(@RequestParam String heroName) {
        return ResponseEntity.ok(heroDurationsService.loadAndSaveHeroDurations(heroName));
    }
}
