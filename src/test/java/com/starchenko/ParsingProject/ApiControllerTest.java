package com.starchenko.ParsingProject;

import com.starchenko.ParsingProject.controller.ApiController;
import com.starchenko.ParsingProject.dto.*;
import com.starchenko.ParsingProject.model.HeroDurationsEntity;
import com.starchenko.ParsingProject.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class ApiControllerTest {
	@Autowired
	private ApiController apiController;  // Контроллер

	@MockBean
	private OpenDotaAPIService openDotaAPIService;  // Мокаем сервисы

	@MockBean
	private ExcelExportService excelExportService;

	@MockBean
	private HeroService heroService;

	@MockBean
	private PlayerService playerService;

	@MockBean
	private HeroDurationsService heroDurationsService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		// Создаем MockMvc вручную
		mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
	}

	@Test
	void testGetHeroes() throws Exception {
		Hero hero1 = new Hero(1,
				"Hero1",
				"Hero One",
				PrimaryAttribute.forValue("STR"),
				AttackType.MELEE,
				List.of("Carry", "Support"),
				4);
		Hero hero2 = new Hero(2,
				"Hero2",
				"Hero Two",
				PrimaryAttribute.forValue("AGI"),
				AttackType.RANGED,
				List.of("Carry", "Nuker"),
				4);
		List<Hero> heroes = List.of(hero1, hero2);

		when(openDotaAPIService.getHeroes()).thenReturn(heroes);

		mockMvc.perform(get("/api/dota/heroes"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Hero1"))
				.andExpect(jsonPath("$[1].name").value("Hero2"));
	}

	@Test
	void testGetHeroByName() throws Exception {
		Hero hero = new Hero(1,
				"Hero1",
				"Hero One",
				PrimaryAttribute.forValue("STR"),
				AttackType.MELEE,
				List.of("Carry", "Support"),
				4);

		when(openDotaAPIService.getHeroByName("Hero1")).thenReturn(hero);

		mockMvc.perform(get("/api/dota/heroes/Hero1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Hero1"))
				.andExpect(jsonPath("$.localized_name").value("Hero One"));
	}

	@Test
	void testLoadHeroes() throws Exception {
		doNothing().when(heroService).loadAndSaveHeroes();

		mockMvc.perform(post("/api/dota/load/heroes"))
				.andExpect(status().isOk())
				.andExpect(content().string("Герои успешно загружены"));

		verify(heroService, times(1)).loadAndSaveHeroes();
	}

	@Test
	void testLoadPlayer() throws Exception {
		int accountId = 12345;
		doNothing().when(playerService).loadAndSavePlayerById(accountId);

		mockMvc.perform(post("/api/dota/load/players")
						.param("accountId", String.valueOf(accountId)))
				.andExpect(status().isOk())
				.andExpect(content().string("Игрок с accountId " + accountId + " успешно загружен"));

		verify(playerService, times(1)).loadAndSavePlayerById(accountId);
	}

	@Test
	void testLoadHeroDurations() throws Exception {
		HeroDurationsEntity duration1 = new HeroDurationsEntity("300", 100, 60);
		HeroDurationsEntity duration2 = new HeroDurationsEntity("600", 150, 70);
		List<HeroDurationsEntity> durations = List.of(duration1, duration2);

		when(heroDurationsService.loadAndSaveHeroDurations("Hero1")).thenReturn(durations);

		mockMvc.perform(post("/api/dota/load/heroDurations")
						.param("heroName", "Hero1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].duration").value(30))
				.andExpect(jsonPath("$[1].duration").value(40));
	}

	@Test
	void testExportHeroes() throws Exception {
		byte[] excelFile = new byte[]{1, 2, 3, 4};
		when(excelExportService.exportHeroesToExcel()).thenReturn(excelFile);

		mockMvc.perform(get("/api/dota/export/heroes"))
				.andExpect(status().isOk())
				.andExpect(header().string("Content-Disposition", "attachment; filename=\"heroes.xlsx\""))
				.andExpect(content().bytes(excelFile));
	}

	@Test
	void testExportPlayers() throws Exception {
		byte[] excelFile = new byte[]{1, 2, 3, 4};  // Example binary data for the file
		when(excelExportService.exportPlayersToExcel()).thenReturn(excelFile);

		mockMvc.perform(get("/api/dota/export/players"))
				.andExpect(status().isOk())
				.andExpect(header().string("Content-Disposition", "attachment; filename=\"players.xlsx\""))
				.andExpect(content().bytes(excelFile));
	}

	@Test
	void testGetPlayerStatsById() throws Exception {
		Player player = new Player(
				80,
				4999,
				new Player.Profile(
						3253215,
						"Account",
						"RealName",
						"1234567890",
						"avatarFullUrl",
						"https://profile.url"
				)
		);

		when(openDotaAPIService.getPlayerStatsById(3253215)).thenReturn(player);

		mockMvc.perform(get("/api/dota/players/3253215"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.rank_tier").value(80))
				.andExpect(jsonPath("$.leaderboard_rank").value(4999))
				.andExpect(jsonPath("$.profile.account_id").value(3253215))
				.andExpect(jsonPath("$.profile.personaname").value("Account"))
				.andExpect(jsonPath("$.profile.name").value("RealName"))
				.andExpect(jsonPath("$.profile.steamid").value("1234567890"))
				.andExpect(jsonPath("$.profile.avatarfull").value("avatarFullUrl"))
				.andExpect(jsonPath("$.profile.profileurl").value("https://profile.url"));
	}
	@Test
	void testCheckWLForGames() throws Exception {
		WLResponse response = new WLResponse(100, 50);

		when(openDotaAPIService.getWLForGames(12345, 50)).thenReturn(response);

		mockMvc.perform(get("/api/dota/dota/players/12345/wl")
						.param("limit", "50"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.win").value(100))
				.andExpect(jsonPath("$.lose").value(50))
				.andExpect(jsonPath("$.winrate").value("66.67%"));
	}
}
