package com.rishi.journalApp.service;

import com.rishi.journalApp.entity.JournalEntry;
import com.rishi.journalApp.entity.User;
import com.rishi.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional // helps us to achieve the isolation and atomicity property of DBMS
    public void saveEntry(JournalEntry journalEntry, String username) {
        try{
            User user = userService.findByUsername(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);

            user.getJournalEntries().add(saved);
//            user.setUsername(null); // -> we manually tries to abort the transaction to check its working
            userService.saveUser(user);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An error occured while saving journal entry");
//            log.error("Exception ", e);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    @Transactional
    public boolean deleteEntry(ObjectId myId, String username) {
        boolean removed = false;
        try {
            User user = userService.findByUsername(username);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(myId);
            }
        } catch(Exception e) {
//            System.out.println(e.getMessage());
            log.error(e.getMessage());
            throw new RuntimeException("An error occured while deleting journal entry");
        }

        return removed;
    }

//    public List<JournalEntry> findByUsername(String username) {
//
//    }
}

// controller --> service --> repository