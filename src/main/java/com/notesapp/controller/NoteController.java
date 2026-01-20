package com.notesapp.controller;

import com.notesapp.dto.ApiResponse;
import com.notesapp.dto.NoteRequest;
import com.notesapp.dto.NoteResponse;
import com.notesapp.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<ApiResponse> createNote(@Valid @RequestBody NoteRequest noteRequest) {
        NoteResponse noteResponse = noteService.createNote(noteRequest);
        ApiResponse response = new ApiResponse(true, "Note created successfully", noteResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllNotes() {
        List<NoteResponse> notes = noteService.getAllNotes();
        ApiResponse response = new ApiResponse(true, "Notes retrieved successfully", notes);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateNote(@PathVariable Long id, @Valid @RequestBody NoteRequest noteRequest) {
        NoteResponse noteResponse = noteService.updateNote(id, noteRequest);
        ApiResponse response = new ApiResponse(true, "Note updated successfully", noteResponse);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        ApiResponse response = new ApiResponse(true, "Note deleted successfully");
        return ResponseEntity.ok(response);
    }
}
