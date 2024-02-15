package de.neuefische.spring_data_challenges.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/asterix")
@RequiredArgsConstructor
public class AsterixController {

    private final AsterixRepo repo;

    // ############################################################
    // GET
    // ############################################################

    @GetMapping("/characters")
    public List<CharacterRecord> getCharacters(
            @RequestParam(required = false) boolean useOrLogic,
            @RequestParam(required = false) String job,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String name){

        if(useOrLogic){
            return getCombinedResults(job, Optional.ofNullable(age), name);
        }

        return getFullMatchResults(job, Optional.ofNullable(age), name);
    }

    private List<CharacterRecord> getFullMatchResults(String job, Optional<Integer> age, String name) {
        List<CharacterRecord> result = repo.findAll();

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

    private List<CharacterRecord> getCombinedResults(String job, Optional<Integer> age, String name){
        List<CharacterRecord> fullList = repo.findAll();
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

    @GetMapping("/character/{id}")
    public CharacterRecord getCharacterById(@PathVariable String id) {
        return repo.findById(id).get();
    }

    // ############################################################
    // POST
    // ############################################################
    @PostMapping("/addCharacter")
    public CharacterRecord addCharacter(@RequestBody CharacterRecord characterRecord) {
        return repo.save(characterRecord);
    }


    // ############################################################
    // PUT
    // ############################################################
    @PutMapping("/updateCharacter/{id}")
    public CharacterRecord updateCharacter(
            @PathVariable String id,
            @RequestBody CharacterRecord characterRecord
    ) {
        return repo.save(characterRecord);
    }

    // ############################################################
    // DELETE
    // ############################################################
    @DeleteMapping("/deleteCharacter/{id}")
    public String deleteCharacter(@PathVariable String id) {
        String name = repo.findById(id).get().name();
        repo.deleteById(id);
        return name + " wurde aus dem Dorf verbannt!";
    }
}
