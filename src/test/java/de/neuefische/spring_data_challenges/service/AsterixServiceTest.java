package de.neuefische.spring_data_challenges.service;

import de.neuefische.spring_data_challenges.model.CharacterRecord;
import de.neuefische.spring_data_challenges.model.dtos.CharacterDto;
import de.neuefische.spring_data_challenges.repository.AsterixRepo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsterixServiceTest {

    private final AsterixRepo mockRepository = mock(AsterixRepo.class);
    private final IdService mockIdService = mock(IdService.class);
    private final AsterixService service = new AsterixService(mockRepository, mockIdService);

    @Test
    void getAllCharacters() {
        //GIVEN
        List<CharacterRecord> setup = List.of(
                new CharacterRecord("1", "Asterix", 35,"Hero"),
                new CharacterRecord("2", "Obelix", 35,"Hero"),
                new CharacterRecord("3", "Miraculix", 35,"Druid"),
                new CharacterRecord("4", "Idefix", 35,"Dog"),
                new CharacterRecord("5", "Getafix", 35,"Druid")
        );
        List<CharacterRecord> expected = List.of(
                new CharacterRecord("1", "Asterix", 35,"Hero"),
                new CharacterRecord("2", "Obelix", 35,"Hero"),
                new CharacterRecord("3", "Miraculix", 35,"Druid"),
                new CharacterRecord("4", "Idefix", 35,"Dog"),
                new CharacterRecord("5", "Getafix", 35,"Druid")
        );
        when(mockRepository.findAll()).thenReturn(setup);
        //WHEN
        List<CharacterRecord> actual = service.getFullMatchResults(null, Optional.empty(), null);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void getAllCharacters_whenParamJobHero_thenReturnHeroes() {
        //GIVEN
        List<CharacterRecord> setup = List.of(
                new CharacterRecord("1", "Asterix", 35,"Hero"),
                new CharacterRecord("2", "Obelix", 35,"Hero"),
                new CharacterRecord("3", "Miraculix", 35,"Druid"),
                new CharacterRecord("4", "Idefix", 35,"Dog"),
                new CharacterRecord("5", "Getafix", 35,"Druid")
        );
        List<CharacterRecord> expected = List.of(
                new CharacterRecord("1", "Asterix", 35,"Hero"),
                new CharacterRecord("2", "Obelix", 35,"Hero")
        );

        String job = "Hero";
        when(mockRepository.findAll()).thenReturn(setup);
        //WHEN
        List<CharacterRecord> actual = service.getFullMatchResults(job, Optional.empty(), null);
        //THEN
        assertEquals(expected, actual);
        verify(mockRepository).findAll();
    }

    @Test
    void getAllCharacters_whenParamJobHeroAndName_thenReturnSpecificCharacter() {
        //GIVEN
        List<CharacterRecord> setup = List.of(
                new CharacterRecord("1", "Asterix", 35,"Hero"),
                new CharacterRecord("2", "Obelix", 35,"Hero"),
                new CharacterRecord("3", "Miraculix", 35,"Druid"),
                new CharacterRecord("4", "Idefix", 35,"Dog"),
                new CharacterRecord("5", "Getafix", 35,"Druid")
        );
        List<CharacterRecord> expected = List.of(
                new CharacterRecord("1", "Asterix", 35,"Hero")
        );
        String job = "Hero";
        String name = "Asterix";

        when(mockRepository.findAll()).thenReturn(setup);
        //WHEN
        List<CharacterRecord> actual = service.getFullMatchResults(job, Optional.empty(), name);
        //THEN
        assertEquals(expected, actual);
        verify(mockRepository).findAll();
    }

    @Test
    void getAllCharacters_whenParamJobHeroAndName_thenReturnAllMatchesWithOrLogic() {
        //GIVEN
        List<CharacterRecord> setup = List.of(
                new CharacterRecord("1", "Asterix", 35,"Hero"),
                new CharacterRecord("2", "Obelix", 35,"Hero"),
                new CharacterRecord("3", "Miraculix", 35,"Druid"),
                new CharacterRecord("4", "Idefix", 35,"Dog"),
                new CharacterRecord("5", "Getafix", 35,"Druid")
        );
        List<CharacterRecord> expected = List.of(
                new CharacterRecord("1", "Asterix", 35,"Hero"),
                new CharacterRecord("2", "Obelix", 35,"Hero"),
                new CharacterRecord("4", "Idefix", 35,"Dog")
        );
        String job = "Hero";
        String name = "Idefix";

        when(mockRepository.findAll()).thenReturn(setup);
        //WHEN
        List<CharacterRecord> actual = service.getCombinedResults(job, Optional.empty(), name);
        //THEN
        assertEquals(expected, actual);
        verify(mockRepository).findAll();
    }

    @Test
    void findById() {
        //GIVEN
        CharacterRecord expected = new CharacterRecord("1", "Asterix", 35,"Hero");
        when(mockRepository.findById("1")).thenReturn(Optional.of(expected));
        //WHEN
        CharacterRecord actual = service.findById("1");
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void save() {
        //GIVEN
        CharacterDto characterDtoToSave = new CharacterDto("Asterix", 35,"Hero");
        CharacterRecord expected = new CharacterRecord("1", "Asterix", 35,"Hero");
        when(mockIdService.generateUUID()).thenReturn("1");
        when(mockRepository.save(new CharacterRecord("1", "Asterix", 35,"Hero"))).thenReturn(expected);
        //WHEN
        CharacterRecord actual = service.save(characterDtoToSave);
        //THEN
        assertEquals(expected, actual);
        verify(mockRepository).save(new CharacterRecord("1", "Asterix", 35,"Hero"));
        verify(mockIdService).generateUUID();
    }

    @Test
    void update() {
        //GIVEN
        CharacterRecord expected = new CharacterRecord("1", "Asterix", 35,"Hero");
        when(mockRepository.existsById("1")).thenReturn(true);
        when(mockRepository.save(expected)).thenReturn(expected);
        //WHEN
        CharacterRecord actual = service.update(new CharacterDto("Asterix", 35,"Hero"), "1");
        //THEN
        assertEquals(expected, actual);
        verify(mockRepository).existsById("1");
        verify(mockRepository).save(expected);
    }


    @Test
    void deleteById() {
        //GIVEN
        CharacterRecord expected = new CharacterRecord("1", "Asterix", 35,"Hero");
        when(mockRepository.findById("1")).thenReturn(Optional.of(expected));
        //WHEN
        CharacterRecord actual = service.deleteById("1");
        //THEN
        assertEquals(expected, actual);
        verify(mockRepository).deleteById("1");
    }
}