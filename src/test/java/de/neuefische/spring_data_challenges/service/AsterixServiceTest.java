package de.neuefische.spring_data_challenges.service;

import de.neuefische.spring_data_challenges.repository.AsterixRepo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AsterixServiceTest {

    private final AsterixRepo repository = mock(AsterixRepo.class);
    private final IdService idService = mock(IdService.class);

    @Test
    void getAllCharacters() {
        //GIVEN

        //WHEN
        //THEN
    }

    @Test
    void update() {
    }

    @Test
    void findById() {
    }

    @Test
    void deleteById() {
    }
}