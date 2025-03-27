package com.rishi.journalApp.controller;

import com.rishi.journalApp.entity.JournalEntry;
import com.rishi.journalApp.entity.User;
import com.rishi.journalApp.service.JournalEntryService;
import com.rishi.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journalv2")
public class JavaEntryControllerV2 {
    //    special type of classes or components handle HTTP requests
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    // get the journal entries of specific user after the authorization done
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() { // localhost:8080/journal GET
        // Authorization implemented here when you authorized then only you can access all journals
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User userFromDB = userService.findByUsername(username);
        List<JournalEntry> allEntries = userFromDB.getJournalEntries();

        if (allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // create a post of specific user after authorization
    @PostMapping
    public ResponseEntity<?> create_entry(@RequestBody JournalEntry myEntry) { // localhost:8080/journal POST
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // get the journal entry of user by its id after authorization
    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // find the user journal entry linked with its own not display the journal entries of other users
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(myId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // delete the journal entry of user after its authorization
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean removed = journalEntryService.deleteEntry(myId, username);
        if(removed) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Update the journal entry of user after authorization
    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> updateEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // find the user journal entry linked with its own not display the journal entries of other users
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(myId);
            if (journalEntry.isPresent()) {
                JournalEntry oldentry = journalEntry.get();
                oldentry.setTitle(!newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldentry.getTitle());
                oldentry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldentry.getContent());
                journalEntryService.saveEntry(oldentry);
                return new ResponseEntity<>(oldentry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
