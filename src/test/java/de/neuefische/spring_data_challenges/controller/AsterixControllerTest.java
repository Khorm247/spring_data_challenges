package de.neuefische.spring_data_challenges.controller;

import de.neuefische.spring_data_challenges.model.CharacterRecord;
import de.neuefische.spring_data_challenges.repository.AsterixRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class AsterixControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AsterixRepo repo;
    @Test
    void getCharacters() throws Exception {
        List<CharacterRecord> characterList = List.of();
        mvc.perform(MockMvcRequestBuilders.get("/api/asterix/characters"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(characterList.toString()));
    }

    @Test
    void getCharacterById() throws Exception {
        repo.save(new CharacterRecord("1", "Asterix", 35,"Hero"));
        mvc.perform(MockMvcRequestBuilders.get("/api/asterix/character/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {                            
                            "name": "Asterix",
                            "age": 35,
                            "job": "Hero"
                        }
                        """))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void addCharacter() {
    }

    @Test
    void updateCharacter() {
    }

    @Test
    void deleteCharacter() {
    }
}