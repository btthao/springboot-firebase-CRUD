package com.app.firebasecrud.character.controller;

import com.app.firebasecrud.character.service.CharacterService;
import com.app.firebasecrud.character.entity.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/characters")
@Lazy
public class CharacterController {
    @Autowired
    private CharacterService service;

    @PostMapping
    public String addCharacter(@RequestBody Character character) throws ExecutionException, InterruptedException {
        return service.addCharacter(character);
    }

    @GetMapping
    public List<Character> getAllCharacters() throws ExecutionException, InterruptedException {
        return service.getAllCharacters();
    }

}
