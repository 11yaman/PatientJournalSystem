package com.example.backend.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(Long noteId) {
        super("Note with id " + noteId + " was not found");
    }
}
