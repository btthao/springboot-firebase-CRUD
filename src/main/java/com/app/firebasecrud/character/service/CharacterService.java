package com.app.firebasecrud.character.service;

import com.app.firebasecrud.character.entity.Character;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class CharacterService {
    private Firestore firestore;
    private static final String COLLECTION_NAME = "characters";


    public String addCharacter(Character character) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collectionApiFuture = firestore.collection(COLLECTION_NAME).document().set(character);

        return "Character added at " + collectionApiFuture.get().getUpdateTime().toString();
    }

    public List<Character> getAllCharacters() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = firestore.collection(COLLECTION_NAME).get();
        List<Character> charactersList = new ArrayList<>();

        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            Character character = document.toObject(Character.class);
            character.setId(document.getId());
            charactersList.add(character);
        }

        return charactersList;
    }

    public Character getCharacterById(String characterId) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = firestore.collection(COLLECTION_NAME).document(characterId).get().get();

        if (!document.exists()) return null;

        Character character = document.toObject(Character.class);

        Objects.requireNonNull(character).setId(characterId);

        return character;
    }

    public String updateCharacter(String characterId, Character character) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection(COLLECTION_NAME).document(characterId);
        
        if (!document.get().get().exists()) return null;
        
        ApiFuture<WriteResult> collectionApiFuture = document.set(character);

        return "Character with id " + characterId +  " updated at " + collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteCharacter(String characterId) throws ExecutionException, InterruptedException {
        DocumentReference document = firestore.collection(COLLECTION_NAME).document(characterId);

        if (!document.get().get().exists()) return null;

        document.delete();

        return "Character with id " + characterId +  " deleted successfully";
    }

}
