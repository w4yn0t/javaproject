package com.starchenko.ParsingProject.service;

import com.starchenko.ParsingProject.dto.Hero;
import com.starchenko.ParsingProject.model.HeroEntity;
import com.starchenko.ParsingProject.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeroService {
    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private OpenDotaAPIService openDotaAPIService;

    public void loadAndSaveHeroes() {
        List<Hero> heroesFromApi = openDotaAPIService.getHeroes();

        List<HeroEntity> heroEntities = heroesFromApi.stream().map(
                hero -> new HeroEntity(
                        hero.getId(),
                        hero.getName(),
                        hero.getLocalizedName(),
                        hero.getPrimaryAttr().toString(),
                        hero.getAttackType().toString(),
                        String.join(", ", hero.getRoles())
                )).collect(Collectors.toList());

        heroRepository.saveAll(heroEntities);
    }

    public List<HeroEntity> getAllHeroes() {
        return heroRepository.findAll();
    }

    public HeroEntity getHeroByLocalizedName(String localizedName) {
        return heroRepository.findByLocalizedNameIgnoreCase(localizedName);
    }
}
