package com.starchenko.ParsingProject.service;

import com.starchenko.ParsingProject.dto.Player;
import com.starchenko.ParsingProject.dto.WLResponse;
import com.starchenko.ParsingProject.model.PlayerEntity;
import com.starchenko.ParsingProject.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private OpenDotaAPIService openDotaAPIService;

    public void loadAndSavePlayerById(int accountId) {
        Player playerFromApi = openDotaAPIService.getPlayerStatsById(accountId);
        WLResponse playerWLFromApi = openDotaAPIService.getWLForGames(accountId, 100);

        PlayerEntity playerEntity = new PlayerEntity(
                playerFromApi.getProfile().getAccountId(),
                playerFromApi.getProfile().getPersonName(),
                playerFromApi.getProfile().getRealName(),
                playerFromApi.getProfile().getSteamId(),
                playerFromApi.getProfile().getAvatarFull(),
                playerFromApi.getProfile().getProfileUrl(),
                playerFromApi.getRankTier(),
                playerFromApi.getLeaderboardRank(),
                playerWLFromApi.getWin(),
                playerWLFromApi.getLose(),
                playerWLFromApi.getWinrate()
        );

        playerRepository.save(playerEntity);
    }

    public List<PlayerEntity> getAllPlayers() {
        return playerRepository.findAll();
    }

    public PlayerEntity getPlayerById(int accountId) {
        return playerRepository.findByAccountId(accountId);
    }
}
