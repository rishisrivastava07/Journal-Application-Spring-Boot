package com.rishi.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;

//    Parent child relation is established
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();

//    Authorization part -> user is assigned which roles (like simple user or admin or anything else)
    private List<String> roles;

    public User() {

    }
}
