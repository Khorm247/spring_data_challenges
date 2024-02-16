package de.neuefische.spring_data_challenges.controller;

import de.neuefische.spring_data_challenges.model.CharacterRecord;
import de.neuefische.spring_data_challenges.model.dtos.CharacterDto;
import de.neuefische.spring_data_challenges.repository.AsterixRepo;
import de.neuefische.spring_data_challenges.service.AsterixService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/api/asterix")
@RequiredArgsConstructor
public class AsterixController {

    private final AsterixService service;

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
            return service.getCombinedResults(job, Optional.ofNullable(age), name);
        }

        return service.getFullMatchResults(job, Optional.ofNullable(age), name);
    }

    @GetMapping("/character/{id}")
    public CharacterRecord getCharacterById(@PathVariable String id) {
        return service.findById(id);
    }

    // ############################################################
    // POST
    // ############################################################
    @PostMapping("/addCharacter")
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterRecord addCharacter(@RequestBody CharacterDto characterDto) {
        return service.save(characterDto);
    }


    // ############################################################
    // PUT
    // ############################################################
    @PutMapping("/updateCharacter/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CharacterRecord updateCharacter(
            @PathVariable String id,
            @RequestBody CharacterDto characterDto
    ) {
        return service.update(characterDto, id);
    }

    // ############################################################
    // DELETE
    // ############################################################
    @DeleteMapping("/deleteCharacter/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteCharacter(@PathVariable String id) {
        CharacterRecord deleted = service.deleteById(id);
        return deleted.name() + " wurde aus dem Dorf verbannt!";
    }
}
