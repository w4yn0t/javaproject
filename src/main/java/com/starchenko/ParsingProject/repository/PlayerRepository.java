package com.starchenko.ParsingProject.repository;

import com.starchenko.ParsingProject.model.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {
    PlayerEntity findByAccountId(int accountId);

}
