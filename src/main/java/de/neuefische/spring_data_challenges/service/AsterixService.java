package de.neuefische.spring_data_challenges.service;

import de.neuefische.spring_data_challenges.model.CharacterRecord;
import de.neuefische.spring_data_challenges.model.dtos.CharacterDto;
import de.neuefische.spring_data_challenges.repository.AsterixRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AsterixService {
    private final AsterixRepo repository;
    private final IdService idService;

    public List<CharacterRecord> getFullMatchResults(String job, Optional<Integer> age, String name) {
        List<CharacterRecord> result = repository.findAll();

        if (job != null) {
            result = result.stream()
                    .filter(characterRecord -> characterRecord.job().equals(job))
                    .toList();
        }

        if (age.isPresent()) {
            result = result.stream()
                    .filter(characterRecord -> characterRecord.age() == age.get())
                    .toList();
        }

        if (name != null) {
            result = result.stream()
                    .filter(characterRecord -> characterRecord.name().equals(name))
                    .toList();
        }
        return result;
    }

    public List<CharacterRecord> getCombinedResults(String job, Optional<Integer> age, String name){
        List<CharacterRecord> fullList = repository.findAll();
        List<CharacterRecord> result = new ArrayList<>();

        if (job != null) {
            result.addAll(fullList.stream()
                    .filter(characterRecord -> characterRecord.job().equals(job))
                    .toList());
        }

        if (age.isPresent()) {
            result.addAll(fullList.stream()
                    .filter(characterRecord -> characterRecord.age() == age.get())
                    .toList());
        }

        if (name != null) {
            result.addAll(fullList.stream()
                    .filter(characterRecord -> characterRecord.name().equals(name))
                    .toList());
        }
        return result.stream().distinct().toList();
    }

    public CharacterRecord save(CharacterDto characterDto) {
        return repository.save(new CharacterRecord(idService.generateUUID(), characterDto.getName(), characterDto.getAge(), characterDto.getJob()));
    }

    public CharacterRecord update(CharacterDto characterDto, String id) {
        if(repository.existsById(id)){
            return repository.save(new CharacterRecord(id, characterDto.getName(), characterDto.getAge(), characterDto.getJob()));
        }
        throw new NoSuchElementException("No such element, no character with id: " + id);
    }

    public CharacterRecord findById(String id) {
        return repository.findById(id).orElseThrow();
    }

    public CharacterRecord deleteById(String id) {
        CharacterRecord deleted = repository.findById(id).orElseThrow();
        repository.deleteById(id);
        return deleted;
    }
}
