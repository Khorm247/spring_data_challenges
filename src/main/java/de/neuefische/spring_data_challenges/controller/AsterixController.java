package de.neuefische.spring_data_challenges.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestParam(required = false) String job,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String name){
        if (job != null) {
            return repo.findByJob(job);
        }
        if (age != null) {
            return repo.findByAge(age);
        }
        if (name != null) {
            return repo.findByName(name);
        }
        return repo.findAll();
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
