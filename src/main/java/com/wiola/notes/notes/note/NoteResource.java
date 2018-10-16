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
import java.util.stream.Collectors;

@RestController
public class NoteResource {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteResource(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    @GetMapping("/notes")
    public List<Note> getAllNotes() {
        return noteRepository.findAll().stream()
                .filter(note -> !note.isArchivedNoteVersion() && !note.isDeleted())
                .collect(Collectors.toList());
    }

    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable(value = "id") Long id) {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (!noteOptional.isPresent() || noteOptional.get().isDeleted())
            throw new NoteNotFoundException("id: " + id);

        return noteOptional.get();
    }

    @PostMapping("/notes")
    public ResponseEntity<Object> createNote(@Valid @RequestBody Note note) {
        createInitialNote(note);
        Note savedNote = noteRepository.save(note);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedNote.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/notes/{id}")
    public Note updateNote(@Valid @RequestBody Note note, @PathVariable(value = "id") Long id) {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (!noteOptional.isPresent() || noteOptional.get().isDeleted())
            throw new NoteNotFoundException("id: " + id);

        Note updatedNote = noteOptional.get();
        updatedNote.setIdOfParentNote(id);
        Note archivedNote = createArchivedNote(updatedNote);
        archivedNote.setIdOfParentNote(id);
        noteRepository.save(archivedNote);
        setUpdatedNoteFields(updatedNote, note);

        return noteRepository.save(noteOptional.get());
    }

    @DeleteMapping("/notes/{id}")
    public void deleteNote(@PathVariable(value = "id") Long id) {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (!noteOptional.isPresent() || noteOptional.get().isDeleted())
            throw new NoteNotFoundException("id: " + id);

        noteOptional.get().setDeleted(true);
        noteRepository.save(noteOptional.get());
    }

    private void createInitialNote(Note note) {
        note.setCreationDate(LocalDateTime.now());
        note.setModificationDate(LocalDateTime.now());
        note.setVersionNumber(0);
        note.setArchivedNoteVersion(false);
        note.setDeleted(false);
    }

    private Note createArchivedNote(Note note) {
        Note archivedNote = new Note(note);
        archivedNote.setIdOfParentNote(note.getId());
        archivedNote.setArchivedNoteVersion(true);

        return archivedNote;
    }

    private void setUpdatedNoteFields(Note updatedNote, Note note) {
        updatedNote.setTitle(note.getTitle());
        updatedNote.setContent(note.getContent());
        updatedNote.setVersionNumber(updatedNote.getVersionNumber() + 1);
        updatedNote.setModificationDate(LocalDateTime.now());
    }
}
