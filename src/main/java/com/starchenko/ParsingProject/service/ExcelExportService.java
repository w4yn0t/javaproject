package com.starchenko.ParsingProject.service;

import com.starchenko.ParsingProject.model.HeroDurationsEntity;
import com.starchenko.ParsingProject.model.HeroDurationsNestedEntity;
import com.starchenko.ParsingProject.model.HeroEntity;
import com.starchenko.ParsingProject.model.PlayerEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
public class ExcelExportService {

    @Autowired
    private HeroService heroService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private HeroDurationsService heroDurationsService;

    public byte[] exportHeroesToExcel() throws IOException {
        List<HeroEntity> heroes = heroService.getAllHeroes();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Heroes");

        String[] columns = {
                "ID",
                "Name",
                "Localized Name",
                "Primary Attr",
                "Attack Type",
                "Roles",
        };

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int rowIdx = 1;
        for (HeroEntity hero : heroes) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(hero.getId());
            row.createCell(1).setCellValue(hero.getName());
            row.createCell(2).setCellValue(hero.getLocalizedName());
            row.createCell(3).setCellValue(hero.getPrimaryAttr());
            row.createCell(4).setCellValue(hero.getAttackType());
            row.createCell(5).setCellValue(hero.getRoles());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();
        return baos.toByteArray();
    }

    public byte[] exportPlayersToExcel() throws IOException {
        List<PlayerEntity> players = playerService.getAllPlayers();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Players");

        String[] columns = {
                "Account ID",
                "Persona Name",
                "Real Name",
                "Steam ID",
                "Avatar Full",
                "Profile URL",
                "Rank Tier",
                "Leaderboard Rank",
                "Win",
                "Lose",
                "Winrate"
        };

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int rowIdx = 1;
        for (PlayerEntity player : players) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(player.getAccountId());
            row.createCell(1).setCellValue(player.getPersonName());
            row.createCell(2).setCellValue(player.getRealName());
            row.createCell(3).setCellValue(player.getSteamId());
            row.createCell(4).setCellValue(player.getAvatarFull());
            row.createCell(5).setCellValue(player.getProfileUrl());
            row.createCell(6).setCellValue(player.getRankTier());
            row.createCell(7).setCellValue(player.getLeaderboardRank());
            row.createCell(8).setCellValue(player.getWin());
            row.createCell(9).setCellValue(player.getLose());
            row.createCell(10).setCellValue(player.getWinrate());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    }

    public byte[] exportHeroDurationsToExcel() throws IOException {
        List<HeroDurationsNestedEntity> nestedEntities = heroDurationsService.getAllHeroDurationsNested();
        System.out.println(nestedEntities);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hero Durations");

        String[] columns = {
                "Duration Bin (minutes)",
                "Games Played",
                "Wins"
        };

        int rowIdx = 0;
        for (HeroDurationsNestedEntity nested : nestedEntities) {
            Row heroNameRow = sheet.createRow(rowIdx++);
            Cell heroNameCell = heroNameRow.createCell(0);
            heroNameCell.setCellValue(nested.getHeroName());

            Row headerRow = sheet.createRow(rowIdx++);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            List<HeroDurationsEntity> durations = nested.getHeroDurationsEntities();
            durations.sort(Comparator.comparingInt(d -> Integer.parseInt(d.getDurationBin())));

            for (HeroDurationsEntity hd : durations) {
                Row row = sheet.createRow(rowIdx++);
                int durationInMinutes = Integer.parseInt(hd.getDurationBin()) / 60;
                row.createCell(0).setCellValue(durationInMinutes);
                row.createCell(1).setCellValue(hd.getGamesPlayed());
                row.createCell(2).setCellValue(hd.getWins());
            }

            rowIdx++;
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();
        return baos.toByteArray();
    }
}
