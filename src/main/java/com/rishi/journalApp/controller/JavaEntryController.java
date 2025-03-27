package com.rishi.journalApp.controller;

import com.rishi.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JavaEntryController {
//    special type of classes or components handle HTTP requests

    // it is like a table to store temporary
//    private Map<String, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){ // localhost:8080/journal GET
        return null;
//        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){ // localhost:8080/journal POST
        // @RequestBody basically takes that input data from user and make us avail in code for use
//        journalEntries.put(myEntry.getId(), myEntry);
        return true;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getEntryById(@PathVariable long myId){
//        return journalEntries.get(myId);
        return null;
    }

    @DeleteMapping("id/{myId}")
    public List<JournalEntry> deleteEntryById(@PathVariable long myId){
//        journalEntries.remove(myId);
//        return new ArrayList<>(journalEntries.values());
        return null;
    }

    @PutMapping("id/{myId}")
    public JournalEntry updateEntryById(@PathVariable String myId, @RequestBody JournalEntry myEntry){
//        journalEntries.put(myId, myEntry);
//        return journalEntries.get(myId);
        return null;
    }
}
