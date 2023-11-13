package com.app.firebasecrud.character.service;

import com.app.firebasecrud.character.entity.Character;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Lazy
public class CharacterService {
    private static final String COLLECTION_NAME = "characters";
    private final CollectionReference collection;

    public CharacterService() {
        Firestore firestore = FirestoreClient.getFirestore();
        collection = firestore.collection(COLLECTION_NAME);
    }

    public String addCharacter(Character character) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collectionApiFuture = collection.document().set(character);

        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public List<Character> getAllCharacters() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshot = collection.get();
        List<Character> charactersList = new ArrayList<>();

        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            Character character = document.toObject(Character.class);
            charactersList.add(character);
        }

        return charactersList;
    }

}
