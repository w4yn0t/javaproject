package com.starchenko.ParsingProject.repository;

import com.starchenko.ParsingProject.model.HeroDurationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroDurationsRepository extends JpaRepository<HeroDurationsEntity, Integer> {
}
