package com.starchenko.ParsingProject.repository;

import com.starchenko.ParsingProject.model.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<HeroEntity, Integer> {
    HeroEntity findByLocalizedNameIgnoreCase(String localizedName);
}
