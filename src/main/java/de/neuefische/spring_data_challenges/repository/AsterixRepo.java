package de.neuefische.spring_data_challenges.repository;

import de.neuefische.spring_data_challenges.model.CharacterRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsterixRepo extends MongoRepository<CharacterRecord, String> {
    List<CharacterRecord> findByJob(String job);

    List<CharacterRecord> findByAge(Integer age);

    List<CharacterRecord> findByName(String name);
}
