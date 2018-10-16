package com.wiola.notes.notes.note;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")

// creationDate and modificationDate should be filled automatically
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"creationDate", "modificationDate"}, allowGetters = true)

public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private String content;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modificationDate;

    private Integer versionNumber;
    private Long idOfParentNote; //for old versions of notes
    private boolean isArchivedNoteVersion;
    private boolean isDeleted;

    public Note() {
    }

    public Note(@NotBlank String title, @NotNull String content, LocalDateTime creationDate, LocalDateTime modificationDate,
                Integer versionNumber) {
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.versionNumber = versionNumber;
    }

    public Note(Note note) {
        this.title = note.title;
        this.content = note.content;
        this.creationDate = note.creationDate;
        this.modificationDate = note.modificationDate;
        this.versionNumber = note.versionNumber;
        this.isDeleted = note.isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Long getIdOfParentNote() {
        return idOfParentNote;
    }

    public void setIdOfParentNote(Long idOfParentNote) {
        this.idOfParentNote = idOfParentNote;
    }

    public boolean isArchivedNoteVersion() {
        return isArchivedNoteVersion;
    }

    public void setArchivedNoteVersion(boolean archivedNoteVersion) {
        isArchivedNoteVersion = archivedNoteVersion;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
