package com.starchenko.ParsingProject.service;

import com.starchenko.ParsingProject.dto.HeroDuration;
import com.starchenko.ParsingProject.model.HeroDurationsEntity;
import com.starchenko.ParsingProject.model.HeroDurationsNestedEntity;
import com.starchenko.ParsingProject.repository.HeroDurationsNestedRepository;
import com.starchenko.ParsingProject.repository.HeroDurationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeroDurationsService {

    @Autowired
    private HeroDurationsRepository heroDurationsRepository;
    @Autowired
    private HeroDurationsNestedRepository heroDurationsNestedRepository;
    @Autowired
    private OpenDotaAPIService openDotaAPIService;

    public List<HeroDurationsEntity> loadAndSaveHeroDurations(String heroName) {
        List<HeroDuration> heroDurationsFromApi = openDotaAPIService.getHeroWinrateForDurations(heroName);
        HeroDurationsNestedEntity nestedEntity = new HeroDurationsNestedEntity();

        nestedEntity.setHeroName(heroName);

        List<HeroDurationsEntity> heroDurationsEntities = heroDurationsFromApi.stream().map(
                heroDuration -> new HeroDurationsEntity(
                        heroDuration.getDurationBin(),
                        heroDuration.getGamesPlayed(),
                        heroDuration.getWins()
                )).toList();

        nestedEntity.setHeroDurationsEntities(heroDurationsEntities);

        heroDurationsNestedRepository.save(nestedEntity);
        return getHeroDurationsByHeroName(heroName);
    }

    public List<HeroDurationsNestedEntity> getAllHeroDurationsNested() {
        return heroDurationsNestedRepository.findAll();
    }

    public List<HeroDurationsEntity> getHeroDurationsByHeroName(String heroName) {
        return heroDurationsNestedRepository.findByHeroName(heroName)
                .getHeroDurationsEntities().stream()
                .filter(hero -> hero.getGamesPlayed() > 100)
                .sorted(Comparator.comparingInt(hero -> Integer.parseInt(hero.getDurationBin())))
                .peek(heroDurations -> heroDurations.setDurationBin(String.valueOf(Integer.parseInt(heroDurations.getDurationBin()) / 60)))
                .collect(Collectors.toList());
    }
}
