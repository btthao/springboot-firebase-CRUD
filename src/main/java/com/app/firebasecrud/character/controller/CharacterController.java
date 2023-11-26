package com.app.firebasecrud.character.controller;

import com.app.firebasecrud.character.service.CharacterService;
import com.app.firebasecrud.character.entity.Character;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {
    @Autowired
    private CharacterService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String addCharacter(@Valid @RequestBody Character character) throws ExecutionException, InterruptedException {
        return service.addCharacter(character);
    }

    @GetMapping
    public List<Character> getAllCharacters() throws ExecutionException, InterruptedException {
        return service.getAllCharacters();
    }

    @GetMapping("/{id}")
    public Character getCharacterById(@PathVariable String id) throws ExecutionException, InterruptedException {
        Character character = service.getCharacterById(id);

        if (character == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found.");
        }

        return character;
    }


    @PutMapping("/{id}")
    public String updateCharacter(@PathVariable String id, @Valid @RequestBody Character character) throws ExecutionException, InterruptedException {
        String response = service.updateCharacter(id, character);

        if (response == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found.");
        }

        return response;
    }


    @DeleteMapping("/{id}")
    public String deleteCharacter(@PathVariable String id) throws ExecutionException, InterruptedException {
        String response = service.deleteCharacter(id);

        if (response == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found.");
        }

        return response;
    }

}
