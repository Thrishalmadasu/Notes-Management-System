package com.notesapp.service;

import com.notesapp.dto.NoteRequest;
import com.notesapp.dto.NoteResponse;
import com.notesapp.entity.Note;
import com.notesapp.exception.ResourceNotFoundException;
import com.notesapp.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;

    @Transactional
    public NoteResponse createNote(NoteRequest noteRequest) {
        log.info("Creating new note with title: {}", noteRequest.getTitle());
        
        Note note = new Note();
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        
        Note savedNote = noteRepository.save(note);
        log.info("Note created successfully with id: {}", savedNote.getId());
        
        return mapToResponse(savedNote);
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> getAllNotes() {
        log.info("Fetching all notes");
        List<Note> notes = noteRepository.findAll();
        log.info("Found {} notes", notes.size());
        
        return notes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NoteResponse getNoteById(Long id) {
        log.info("Fetching note with id: {}", id);
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        
        return mapToResponse(note);
    }

    @Transactional
    public void deleteNote(Long id) {
        log.info("Deleting note with id: {}", id);
        
        if (!noteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Note not found with id: " + id);
        }
        
        noteRepository.deleteById(id);
        log.info("Note deleted successfully with id: {}", id);
    }

    private NoteResponse mapToResponse(Note note) {
        NoteResponse response = new NoteResponse();
        response.setId(note.getId());
        response.setTitle(note.getTitle());
        response.setContent(note.getContent());
        response.setCreatedAt(note.getCreatedAt());
        response.setUpdatedAt(note.getUpdatedAt());
        return response;
    }
}
