package de.neuefische.spring_data_challenges.service;

import de.neuefische.spring_data_challenges.model.CharacterRecord;
import de.neuefische.spring_data_challenges.repository.AsterixRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AsterixService {
    private final AsterixRepo repository;

    public List<CharacterRecord> getAllCharacters() {
        return repository.findAll();
    }

    public CharacterRecord addAsterix(CharacterRecord character) {
        repository.save(character);
        return repository.findById(character.id()).orElseThrow();
    }
}
