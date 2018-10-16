package com.wiola.notes.notes.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class NoteResource {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteResource(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping("/notes")
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable(value = "id") Long id) {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (!noteOptional.isPresent())
            throw new NoteNotFoundException("id: " + id);

        return noteOptional.get();
    }

    @PostMapping("/notes")
    public ResponseEntity<Object> createNote(@Valid @RequestBody Note note) {
        note.setCreationDate(LocalDateTime.now());
        note.setModificationDate(LocalDateTime.now());
        Note savedNote = noteRepository.save(note);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedNote.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/notes/{id}")
    public Note updateNote(@Valid @RequestBody Note note, @PathVariable (value = "id") Long id) {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (!noteOptional.isPresent())
            throw new NoteNotFoundException("id: "+ id);

        noteOptional.get().setTitle(note.getTitle());
        noteOptional.get().setContent(note.getContent());
        noteOptional.get().setModificationDate(LocalDateTime.now());

        return noteRepository.save(noteOptional.get());
    }

    @DeleteMapping("/notes/{id}")
    public void deleteNote(@PathVariable(value = "id") Long id) {
        noteRepository.deleteById(id);
    }

}
