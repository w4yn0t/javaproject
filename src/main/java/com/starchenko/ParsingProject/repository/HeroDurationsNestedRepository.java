package com.starchenko.ParsingProject.repository;

import com.starchenko.ParsingProject.model.HeroDurationsNestedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroDurationsNestedRepository extends JpaRepository<HeroDurationsNestedEntity, Integer> {
    HeroDurationsNestedEntity findByHeroName(String heroName);
}
