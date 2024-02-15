package de.neuefische.spring_data_challenges.controller;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AsterixRepo extends MongoRepository<CharacterRecord, String> {
    List<CharacterRecord> findByJob(String job);

    List<CharacterRecord> findByAge(Integer age);

    List<CharacterRecord> findByName(String name);
}
