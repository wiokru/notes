package com.wiola.notes.notes.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This service version returns history of changes of particular note (by id)
 * Even if note was deleted, changes still can be accessed
 */

@RestController
public class NoteHistoryResource {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteHistoryResource(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping("/history/{id}")
    public List<Note> getAllNoteVersions(@PathVariable(value = "id") Long id) {
        return noteRepository.findAll().stream()
                .filter(noteVersion -> noteVersion.getIdOfParentNote().equals(id))
                .collect(Collectors.toList());
    }
}
